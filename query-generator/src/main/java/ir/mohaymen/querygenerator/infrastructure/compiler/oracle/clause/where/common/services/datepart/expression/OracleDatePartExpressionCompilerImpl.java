package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.datepart.expression;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DatePart;

import java.util.Objects;

public class OracleDatePartExpressionCompilerImpl implements OracleDatePartExpressionCompiler {

    private static final String TRUNCATED_DATE = "TRUNC(%s)";
    // a zero padded 24 hour clock keeps the order of time, so a range of time stays a single comparison
    private static final String FORMATTED_TIME = "TO_CHAR(%s, 'HH24:MI')";
    private static final String FORMATTED_TIME_WITH_SECONDS = "TO_CHAR(%s, 'HH24:MI:SS')";
    private static final String EXTRACTED_PART = "EXTRACT(%s FROM %s)";
    // oracle refuses to extract a time part from a DATE column, so the column becomes a timestamp first
    private static final String EXTRACTED_TIME_PART = "EXTRACT(%s FROM CAST(%s AS TIMESTAMP))";

    @Override
    public String compile(String columnReference, DatePart datePart) {
        Objects.requireNonNull(columnReference, "column reference must not be null");
        if (datePart == null) {
            throw new IllegalArgumentException("date part must not be null");
        }

        return switch (datePart) {
            case DATE -> TRUNCATED_DATE.formatted(columnReference);
            case TIME -> FORMATTED_TIME.formatted(columnReference);
            case TIME_WITH_SECONDS -> FORMATTED_TIME_WITH_SECONDS.formatted(columnReference);
            case YEAR, MONTH, DAY -> EXTRACTED_PART.formatted(datePart.name(), columnReference);
            case HOUR, MINUTE, SECOND -> EXTRACTED_TIME_PART.formatted(datePart.name(), columnReference);
        };
    }

}
