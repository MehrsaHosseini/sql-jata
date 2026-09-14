package ir.mohaymen.querygenerator.api.rest.mapping;

import ir.mohaymen.querygenerator.api.rest.common.dto.RawSqlRequest;
import ir.mohaymen.querygenerator.api.rest.common.mapping.SqlRequestMapper;
import ir.mohaymen.querygenerator.application.query.generate.common.model.Query;
import ir.mohaymen.querygenerator.application.query.generate.common.model.SetOperator;
import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;
import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;
import ir.mohaymen.querygenerator.domain.schema.enumeration.JoinType;
import ir.mohaymen.querygenerator.domain.schema.enumeration.SortDirection;
import ir.mohaymen.querygenerator.api.rest.common.dto.ColumnRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.DeleteQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.InsertQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.JoinOnRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.JoinRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.OrderRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.PredicateRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.SelectQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.SetOperationRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.TableRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.UpdateQueryRequest;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SqlRequestMapperTest {

    private final SqlRequestMapper mapper = new SqlRequestMapper();

    @Test
    void selectJoinWhereOrderAndLimit() {
        SelectQueryRequest request = new SelectQueryRequest(
                List.of(
                        new ColumnRequest("E.ID"),
                        new ColumnRequest("E.NAME"),
                        new ColumnRequest("E.HIRE_DATE", "HIRED", DisplayFormatting.TO_JALALI)
                ),
                null,
                new TableRequest("EMPLOYEE", "E"),
                null,
                List.of(new JoinRequest(
                        JoinType.LEFT,
                        "DEPT",
                        "D",
                        List.of(new JoinOnRequest("E.DEPT_ID", "D.ID")),
                        null,
                        null,
                        null
                )),
                PredicateRequest.and(
                        PredicateRequest.eq("E.STATUS", "ACTIVE"),
                        PredicateRequest.isNull("E.DELETED_AT")
                ),
                null,
                null,
                null,
                List.of(new OrderRequest("E.ID", SortDirection.DESC)),
                null,
                10,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Query query = mapper.toSelect(request).generate();
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
    void unionAll() {
        SelectQueryRequest left = new SelectQueryRequest(
                List.of(new ColumnRequest("ID")),
                null,
                new TableRequest("EMPLOYEE"),
                null,
                null,
                PredicateRequest.eq("STATUS", "ACTIVE"),
                null, null, null, null, null,
                null, null, null, null,
                List.of(new SetOperationRequest(
                        SetOperator.UNION_ALL,
                        new SelectQueryRequest(
                                List.of(new ColumnRequest("ID")),
                                null,
                                new TableRequest("CONTRACTOR"),
                                null,
                                null,
                                PredicateRequest.eq("STATUS", "ACTIVE"),
                                null, null, null, null, null,
                                null, null, null, null, null, null, null
                        )
                )),
                null,
                null
        );

        Query query = mapper.toSelect(left).generate();
        assertEquals(
                "SELECT \"ID\" FROM \"EMPLOYEE\" WHERE \"STATUS\" = :p1 "
                        + "UNION ALL SELECT \"ID\" FROM \"CONTRACTOR\" WHERE \"STATUS\" = :p2",
                query.query());
    }

    @Test
    void insertUpdateAndDelete() {
        Query insert = mapper.toInsert(new InsertQueryRequest(
                "EMPLOYEE",
                null,
                List.of("NAME", "SALARY"),
                List.of("ALI", 7500),
                null,
                null
        )).generate();
        assertEquals("INSERT INTO \"EMPLOYEE\" (\"NAME\", \"SALARY\") VALUES (:p1, :p2)", insert.query());

        Map<String, Object> assignments = new LinkedHashMap<>();
        assignments.put("E.SALARY", 8200);
        assignments.put("E.UPDATED_AT", null);
        Query update = mapper.toUpdate(new UpdateQueryRequest(
                "EMPLOYEE",
                "E",
                assignments,
                PredicateRequest.eq("E.ID", 15),
                null,
                null
        )).generate();
        assertEquals(
                "UPDATE \"EMPLOYEE\" \"E\" SET \"E\".\"SALARY\" = :p1, \"E\".\"UPDATED_AT\" = NULL WHERE \"E\".\"ID\" = :p2",
                update.query());

        Query delete = mapper.toDelete(new DeleteQueryRequest(
                "EMPLOYEE",
                "E",
                PredicateRequest.eq("E.ID", 15),
                null,
                null
        )).generate();
        assertEquals("DELETE FROM \"EMPLOYEE\" \"E\" WHERE \"E\".\"ID\" = :p1", delete.query());
    }

    @Test
    void inlineMode() {
        Query query = mapper.toSelect(new SelectQueryRequest(
                List.of(new ColumnRequest("NAME")),
                null,
                new TableRequest("EMPLOYEE"),
                null,
                null,
                PredicateRequest.eq("NAME", "O'BRIEN"),
                null, null, null, null, null,
                null, null, null, null, null,
                ParameterMode.INLINE,
                null
        )).generate();
        assertEquals("SELECT \"NAME\" FROM \"EMPLOYEE\" WHERE \"NAME\" = 'O''BRIEN'", query.query());
    }

    @Test
    void leftJoinFromRawDoesNotRequireTable() {
        Query query = mapper.toSelect(new SelectQueryRequest(
                List.of(new ColumnRequest("t1.ID")),
                null,
                null,
                new RawSqlRequest("(SELECT 1 AS \"ID\", CURRENT_DATE AS \"AS_INSERT_DATE\" FROM DUAL) \"t1\""),
                List.of(new JoinRequest(
                        JoinType.LEFT,
                        null,
                        null,
                        List.of(new JoinOnRequest("t1.AS_INSERT_DATE", "t2.AS_INSERT_DATE")),
                        null,
                        new RawSqlRequest("(SELECT CURRENT_DATE AS \"AS_INSERT_DATE\" FROM DUAL) \"t2\""),
                        null
                )),
                null, null, null, null, null, null,
                null, null, null, null, null, null, null
        )).generate();

        assertEquals(
                "SELECT \"t1\".\"ID\" FROM (SELECT 1 AS \"ID\", CURRENT_DATE AS \"AS_INSERT_DATE\" FROM DUAL) \"t1\" "
                        + "LEFT OUTER JOIN (SELECT CURRENT_DATE AS \"AS_INSERT_DATE\" FROM DUAL) \"t2\" "
                        + "ON \"t1\".\"AS_INSERT_DATE\" = \"t2\".\"AS_INSERT_DATE\"",
                query.query());
    }

    @Test
    void rejectsMixedProjection() {
        assertThrows(IllegalArgumentException.class, () -> mapper.toSelect(new SelectQueryRequest(
                List.of(new ColumnRequest("ID")),
                new RawSqlRequest("COUNT(*)"),
                null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null
        )));
    }
}
