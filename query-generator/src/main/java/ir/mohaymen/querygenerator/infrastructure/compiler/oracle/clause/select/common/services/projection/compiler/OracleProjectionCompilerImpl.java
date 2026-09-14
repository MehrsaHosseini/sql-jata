package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.services.projection.compiler;

import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.select.Select;
import ir.mohaymen.querygenerator.domain.select.SelectColumn;
import ir.mohaymen.querygenerator.domain.select.SelectRaw;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.services.item.compiler.OracleSelectItemCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.services.item.compiler.OracleSelectItemCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleProjectionCompilerImpl implements OracleProjectionCompiler {

    private static final String ALL_COLUMNS = "*";
    private static final String ITEM_SEPARATOR = ", ";

    private final OracleSelectItemCompiler selectItemCompiler;

    public OracleProjectionCompilerImpl() {
        this(new OracleSelectItemCompilerImpl());
    }

    public OracleProjectionCompilerImpl(OracleSelectItemCompiler selectItemCompiler) {
        this.selectItemCompiler = Objects.requireNonNull(selectItemCompiler, "select item compiler must not be null");
    }

    @Override
    public String compile(Select select, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");
        return switch (select) {
            case null -> ALL_COLUMNS;
            case SelectRaw selectRaw -> compileRaw(selectRaw, parameterBinder);
            case SelectColumn selectColumn -> compileColumns(selectColumn.columnList());
        };
    }

    private static String compileRaw(SelectRaw selectRaw, OracleParameterBinder parameterBinder) {
        String raw = selectRaw.raw();
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw select expression must not be null or blank");
        }
        parameterBinder.putAll(selectRaw.parameters());
        return parameterBinder.renderSql(raw.trim());
    }

    private String compileColumns(List<Column> columnList) {
        if (columnList == null || columnList.isEmpty()) {
            return ALL_COLUMNS;
        }

        StringJoiner items = new StringJoiner(ITEM_SEPARATOR);
        for (Column column : columnList) {
            items.add(selectItemCompiler.compile(column));
        }
        return items.toString();
    }

}
