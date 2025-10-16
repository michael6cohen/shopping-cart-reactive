package com.tufin.shoppingcartreactive.config;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider;
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.transaction.ReactiveTransactionManager;

@Configuration
@EnableReactiveNeo4jRepositories(basePackages = "com.tufin.shoppingcartreactive.repository")
public class Neo4jConfig {

    @Bean
    public ReactiveTransactionManager reactiveTransactionManager(Driver driver,
                                                                 ReactiveDatabaseSelectionProvider databaseSelectionProvider) {
        return new ReactiveNeo4jTransactionManager(driver, databaseSelectionProvider);
    }

    @Bean
    public ReactiveDatabaseSelectionProvider customDatabaseSelectionProvider() {
        return ReactiveDatabaseSelectionProvider.getDefaultSelectionProvider();
    }
}
