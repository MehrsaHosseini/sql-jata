package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.union.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.union.Union;
import ir.mohaymen.querygenerator.domain.union.UnionAll;
import ir.mohaymen.querygenerator.domain.union.UnionDistinct;

public class OracleUnionCompilerImpl implements OracleUnionCompiler {

    private static final String UNION_ALL_KEYWORD = "UNION ALL ";
    private static final String UNION_KEYWORD = "UNION ";

    @Override
    public String compile(Union union) {
        return switch (union) {
            case null -> throw new IllegalArgumentException("union must not be null");
            case UnionAll ignored -> UNION_ALL_KEYWORD;
            case UnionDistinct ignored -> UNION_KEYWORD;
        };
    }

}
