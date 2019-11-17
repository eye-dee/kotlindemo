package com.n26.kotlindemo.asyncdemo.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient

@Configuration
open class PostgresConfiguration {
    @Bean
    open fun postgresqlConnectionFactory() = PostgresqlConnectionFactory(
        PostgresqlConnectionConfiguration.builder()
            .host("localhost")
            .database("postgres")
            .username("postgres")
            .password("postgres").build()
    )

    @Bean
    open fun databaseClient(postgresqlConnectionFactory: PostgresqlConnectionFactory) =
        DatabaseClient.create(postgresqlConnectionFactory)
}
