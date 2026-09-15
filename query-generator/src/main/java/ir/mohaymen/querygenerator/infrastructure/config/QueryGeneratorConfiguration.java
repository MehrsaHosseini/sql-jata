package ir.mohaymen.querygenerator.infrastructure.config;

import ir.mohaymen.querygenerator.api.common.mapping.SqlRequestMapper;
import ir.mohaymen.querygenerator.api.public_interface.QueryGeneratorFacade;
import ir.mohaymen.querygenerator.api.public_interface.impl.QueryGeneratorFacadeImpl;
import ir.mohaymen.querygenerator.application.query.generate.QueryGenerator;
import ir.mohaymen.querygenerator.application.query.generate.impl.QueryGeneratorRequestHandlerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.QueryCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.QueryCompilerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(QueryGeneratorProperties.class)
public class QueryGeneratorConfiguration {

    @Bean
    public QueryCompiler queryCompiler(QueryGeneratorProperties properties) {
        return QueryCompilerFactory.create(properties.compiler());
    }

    @Bean
    public QueryGenerator queryGenerator(QueryCompiler queryCompiler) {
        return new QueryGenerator(new QueryGeneratorRequestHandlerImpl(queryCompiler));
    }

    @Bean
    public SqlRequestMapper sqlRequestMapper() {
        return new SqlRequestMapper();
    }

    @Bean
    public QueryGeneratorFacade queryGeneratorFacade(QueryGenerator queryGenerator, SqlRequestMapper mapper) {
        return new QueryGeneratorFacadeImpl(queryGenerator, mapper);
    }
}
