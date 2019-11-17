package com.n26.kotlindemo.asyncdemo.config

import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.DATABASE
import io.r2dbc.spi.ConnectionFactoryOptions.DRIVER
import io.r2dbc.spi.ConnectionFactoryOptions.HOST
import io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD
import io.r2dbc.spi.ConnectionFactoryOptions.PORT
import io.r2dbc.spi.ConnectionFactoryOptions.PROTOCOL
import io.r2dbc.spi.ConnectionFactoryOptions.USER
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient

@Configuration
open class PostgresConfiguration {

    @Bean
    open fun postgresqlConnectionFactory() = ConnectionFactories.get(
        ConnectionFactoryOptions.builder()
            .option(DRIVER, "pool")
            .option(PROTOCOL, "postgresql")
            .option(HOST, "localhost")
            .option(PORT, 5432)
            .option(USER, "postgres")
            .option(PASSWORD, "postgres")
            .option(DATABASE, "postgres")
            .build()
    );

    @Bean
    open fun databaseClient(postgresqlConnectionFactory: ConnectionFactory) =
        DatabaseClient.create(postgresqlConnectionFactory)
}
