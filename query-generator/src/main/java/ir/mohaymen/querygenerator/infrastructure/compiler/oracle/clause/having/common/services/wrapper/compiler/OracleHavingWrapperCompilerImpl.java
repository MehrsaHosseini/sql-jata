package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.wrapper.compiler;

public class OracleHavingWrapperCompilerImpl implements OracleHavingWrapperCompiler {

    private final String WRAPPER_PREFIX = "SELECT * FROM (";
    private final String WRAPPER_SUFFIX = ") ";
    private final String FILTER_KEYWORD = "WHERE ";

    @Override
    public String wrap(String query, String condition) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("the query must be generated before a having condition can wrap it");
        }
        if (condition == null || condition.isBlank()) {
            throw new IllegalArgumentException("the wrapped having condition must not be null or blank");
        }

        return WRAPPER_PREFIX + query.trim() + WRAPPER_SUFFIX + FILTER_KEYWORD + condition;
    }

}
