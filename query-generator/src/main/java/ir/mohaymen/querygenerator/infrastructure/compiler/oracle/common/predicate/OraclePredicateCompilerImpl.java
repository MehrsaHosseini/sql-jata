package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.predicate;

import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.value.OracleValueCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.value.OracleValueCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class OraclePredicateCompilerImpl implements OraclePredicateCompiler {

    private final String OPERAND_SEPARATOR = " ";
    private final String WHITESPACE_PATTERN = "\\s+";
    private final Set<String> RANGE_OPERATIONS = Set.of("BETWEEN", "NOT BETWEEN");

    private final OracleValueCompiler valueCompiler;

    public OraclePredicateCompilerImpl() {
        this(new OracleValueCompilerImpl());
    }

    public OraclePredicateCompilerImpl(OracleValueCompiler valueCompiler) {
        this.valueCompiler = Objects.requireNonNull(valueCompiler, "value compiler must not be null");
    }

    @Override
    public String compile(String expression, String operation, Object value, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(expression, "expression must not be null");

        String requiredOperation = requireOperation(operation);
        String compiledValue = isRange(requiredOperation)
                ? valueCompiler.compileRange(value, parameterBinder)
                : valueCompiler.compile(value, parameterBinder);

        return expression + OPERAND_SEPARATOR + requiredOperation + OPERAND_SEPARATOR + compiledValue;
    }

    private String requireOperation(String operation) {
        if (operation == null || operation.isBlank()) {
            throw new IllegalArgumentException("condition operation must not be null or blank");
        }
        return operation.trim();
    }


    private boolean isRange(String operation) {
        return RANGE_OPERATIONS.contains(operation.toUpperCase(Locale.ROOT).replaceAll(WHITESPACE_PATTERN, OPERAND_SEPARATOR));
    }

}
