package ir.mohaymen.querygenerator.infrastructure.config;

import ir.mohaymen.querygenerator.infrastructure.compiler.QueryCompilerDialect;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "query-generator")
public record QueryGeneratorProperties(@DefaultValue("ORACLE") QueryCompilerDialect compiler) {

    public QueryGeneratorProperties {
        if (compiler == null) {
            compiler = QueryCompilerDialect.ORACLE;
        }
    }
}
