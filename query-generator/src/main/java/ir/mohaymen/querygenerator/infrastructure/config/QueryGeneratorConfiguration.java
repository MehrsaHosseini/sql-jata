package ir.mohaymen.querygenerator.infrastructure.config;

import ir.mohaymen.querygenerator.application.query.generate.QueryGenerator;
import ir.mohaymen.querygenerator.api.rest.common.mapping.SqlRequestMapper;
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
}
