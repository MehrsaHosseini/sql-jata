package ir.mohaymen.querygenerator.application.query.builder;

import ir.mohaymen.querygenerator.application.query.generate.QueryGeneratorRequest;
import ir.mohaymen.querygenerator.application.query.generate.common.model.Query;
import ir.mohaymen.querygenerator.application.query.generate.impl.QueryGeneratorRequestHandlerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.OracleQueryCompiler;
import org.junit.jupiter.api.Test;

import static ir.mohaymen.querygenerator.application.query.builder.Sql.and;
import static ir.mohaymen.querygenerator.application.query.builder.Sql.col;
import static ir.mohaymen.querygenerator.application.query.builder.Sql.desc;
import static ir.mohaymen.querygenerator.application.query.builder.Sql.eq;
import static ir.mohaymen.querygenerator.application.query.builder.Sql.isNull;
import static ir.mohaymen.querygenerator.application.query.builder.Sql.jalali;
import static ir.mohaymen.querygenerator.application.query.builder.Sql.select;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueryBuilderTest {

    @Test
    void selectJoinWhereOrderAndLimit() {
        Query query = select(col("E.ID"), col("E.NAME"), jalali("E.HIRE_DATE", "HIRED"))
                .from("EMPLOYEE", "E")
                .leftJoin("DEPT", "D").on("E.DEPT_ID", "D.ID")
                .where(and(eq("E.STATUS", "ACTIVE"), isNull("E.DELETED_AT")))
                .orderBy(desc("E.ID"))
                .limit(10)
                .generate();

        assertEquals(
                "SELECT \"E\".\"ID\", \"E\".\"NAME\", TO_CHAR(\"E\".\"HIRE_DATE\", 'YYYY/MM/DD', 'NLS_CALENDAR=Persian') AS \"HIRED\" "
                        + "FROM \"EMPLOYEE\" \"E\" "
                        + "LEFT OUTER JOIN \"DEPT\" \"D\" ON \"E\".\"DEPT_ID\" = \"D\".\"ID\" "
                        + "WHERE (\"E\".\"STATUS\" = :p1 AND \"E\".\"DELETED_AT\" IS NULL) "
                        + "ORDER BY \"E\".\"ID\" DESC "
                        + "FETCH NEXT :p2 ROWS ONLY",
                query.query());
        assertEquals("ACTIVE", query.parameters().get("p1"));
        assertEquals(10L, query.parameters().get("p2"));
    }

    @Test
    void unionAllSharesParametersAndAppliesOrderAtTheEnd() {
        Query query = select("ID")
                .from("EMPLOYEE")
                .where(eq("STATUS", "ACTIVE"))
                .unionAll(select("ID").from("CONTRACTOR").where(eq("STATUS", "ACTIVE")))
                .orderBy("ID")
                .generate();

        assertEquals(
                "SELECT \"ID\" FROM \"EMPLOYEE\" WHERE \"STATUS\" = :p1 "
                        + "UNION ALL SELECT \"ID\" FROM \"CONTRACTOR\" WHERE \"STATUS\" = :p2 "
                        + "ORDER BY \"ID\"",
                query.query());
        assertEquals("ACTIVE", query.parameters().get("p1"));
        assertEquals("ACTIVE", query.parameters().get("p2"));
    }

    @Test
    void insertUpdateAndDelete() {
        Query insert = Sql.insertInto("EMPLOYEE")
                .columns("NAME", "SALARY")
                .values("ALI", 7500)
                .generate();
        assertEquals("INSERT INTO \"EMPLOYEE\" (\"NAME\", \"SALARY\") VALUES (:p1, :p2)", insert.query());
        assertEquals("ALI", insert.parameters().get("p1"));
        assertEquals(7500, insert.parameters().get("p2"));

        Query update = Sql.update("EMPLOYEE", "E")
                .set("E.SALARY", 8200)
                .setNull("E.UPDATED_AT")
                .where(eq("E.ID", 15))
                .generate();
        assertEquals(
                "UPDATE \"EMPLOYEE\" \"E\" SET \"E\".\"SALARY\" = :p1, \"E\".\"UPDATED_AT\" = NULL WHERE \"E\".\"ID\" = :p2",
                update.query());
        assertEquals(8200, update.parameters().get("p1"));
        assertEquals(15, update.parameters().get("p2"));

        Query delete = Sql.deleteFrom("EMPLOYEE", "E")
                .where(eq("E.ID", 15))
                .generate();
        assertEquals("DELETE FROM \"EMPLOYEE\" \"E\" WHERE \"E\".\"ID\" = :p1", delete.query());
        assertEquals(15, delete.parameters().get("p1"));
    }

    @Test
    void inlineModeWritesLiterals() {
        Query query = select("NAME")
                .from("EMPLOYEE")
                .where(eq("NAME", "O'BRIEN"))
                .inline()
                .generate();

        assertEquals("SELECT \"NAME\" FROM \"EMPLOYEE\" WHERE \"NAME\" = 'O''BRIEN'", query.query());
        assertTrue(query.parameters().isEmpty());
    }

    @Test
    void handlerRejectsNullRequest() {
        QueryGeneratorRequestHandlerImpl handler = new QueryGeneratorRequestHandlerImpl(new OracleQueryCompiler());
        assertThrows(NullPointerException.class, () -> handler.generateQuery(null));
        assertThrows(NullPointerException.class, () -> new QueryGeneratorRequest(null));
    }

    @Test
    void joinWithoutOnFailsEarly() {
        SelectQuery query = select().from("EMPLOYEE", "E").innerJoin("DEPT", "D");
        assertThrows(IllegalStateException.class, query::generate);
    }
}
