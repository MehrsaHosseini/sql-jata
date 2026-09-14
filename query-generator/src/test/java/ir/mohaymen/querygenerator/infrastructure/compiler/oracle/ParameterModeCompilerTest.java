package ir.mohaymen.querygenerator.infrastructure.compiler.oracle;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.from.FromRaw;
import ir.mohaymen.querygenerator.domain.from.FromTable;
import ir.mohaymen.querygenerator.domain.limit_offset.Page_size;
import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;
import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.table.Table;
import ir.mohaymen.querygenerator.domain.select.SelectColumn;
import ir.mohaymen.querygenerator.domain.where.BasicWhere;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParameterModeCompilerTest {

    private final OracleQueryCompiler compiler = new OracleQueryCompiler();

    @Test
    void namedModeKeepsPlaceholdersForJdbcTemplate() {
        QueryContext context = vatReturn(ParameterMode.NAMED);
        compile(context);

        String sql = context.query().toString();
        assertTrue(sql.contains(":decId"));
        assertTrue(sql.contains(":p1"));
        assertTrue(sql.contains("OFFSET :p2 ROWS"));
        assertEquals("0xABC", context.parameters().get("decId"));
        assertEquals("SUBMITTED", context.parameters().get("p1"));
    }

    @Test
    void inlineModeWritesLiteralsForCopyPaste() {
        QueryContext context = vatReturn(ParameterMode.INLINE);
        compile(context);

        String sql = context.query().toString();
        assertFalse(sql.contains(":decId"));
        assertFalse(sql.contains(":p1"));
        assertTrue(sql.contains("LENGTH('0xABC')"));
        assertTrue(sql.contains("\"STATUS\" = 'SUBMITTED'"));
        assertTrue(sql.contains("OFFSET 20 ROWS FETCH NEXT 10 ROWS ONLY"));
        assertEquals("0xABC", context.parameters().get("decId"));
    }

    @Test
    void inlineModeEscapesQuotesInStrings() {
        QueryContext context = new QueryContext(
                new SelectColumn(List.of(new Column("NAME"))),
                new FromTable(new Table("EMPLOYEE", null)),
                new BasicWhere(new Column("NAME"), "=", "O'BRIEN")
        ).withParameterMode(ParameterMode.INLINE);
        compile(context);

        assertTrue(context.query().toString().contains("\"NAME\" = 'O''BRIEN'"));
    }

    private QueryContext vatReturn(ParameterMode mode) {
        return new QueryContext(
                new SelectColumn(List.of(new Column("t1.GUID"))),
                new FromRaw(
                        "(SELECT \"t0\".* FROM \"DW_DM_VAT_RETURNS\" \"t0\" WHERE LENGTH(:decId) = 34 OR \"t0\".\"GUID\" = :decId) \"t1\"",
                        Map.of("decId", "0xABC")
                ),
                new BasicWhere(new Column("t1.STATUS"), "=", "SUBMITTED"),
                new Page_size(2, 10)
        ).withParameterMode(mode);
    }

    private void compile(QueryContext context) {
        compiler.generateSelect(context);
        compiler.generateFrom(context);
        compiler.generateWhere(context);
        compiler.generatePagination(context);
    }
}
