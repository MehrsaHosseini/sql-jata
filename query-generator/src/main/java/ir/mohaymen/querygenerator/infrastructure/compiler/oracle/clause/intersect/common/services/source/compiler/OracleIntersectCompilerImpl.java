package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.intersect.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.intersect.Intersect;
import ir.mohaymen.querygenerator.domain.intersect.IntersectDistinct;

public class OracleIntersectCompilerImpl implements OracleIntersectCompiler {

    private static final String INTERSECT_KEYWORD = "INTERSECT ";

    @Override
    public String compile(Intersect intersect) {
        return switch (intersect) {
            case null -> throw new IllegalArgumentException("intersect must not be null");
            case IntersectDistinct ignored -> INTERSECT_KEYWORD;
        };
    }

}
