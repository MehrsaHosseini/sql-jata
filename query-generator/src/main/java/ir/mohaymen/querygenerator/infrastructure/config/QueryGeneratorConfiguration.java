package ir.mohaymen.querygenerator.infrastructure.config;

import ir.mohaymen.querygenerator.api.public_interface.QueryGeneratorFacade;
import ir.mohaymen.querygenerator.api.public_interface.impl.QueryGeneratorFacadeImpl;
import ir.mohaymen.querygenerator.api.common.mapping.SqlRequestMapper;
import ir.mohaymen.querygenerator.application.query.generate.QueryGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryGeneratorConfiguration {

    @Bean
    public QueryGenerator queryGenerator() {
        return QueryGenerator.oracle();
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
