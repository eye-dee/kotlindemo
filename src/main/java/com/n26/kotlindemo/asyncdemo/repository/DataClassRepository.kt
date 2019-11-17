package com.n26.kotlindemo.asyncdemo.repository

import com.n26.kotlindemo.pojo.DataClass
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.slf4j.LoggerFactory
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DataClassRepository(
    private val databaseClient: DatabaseClient
) {

    private val log = LoggerFactory.getLogger(DataClassRepository::class.java)

    fun insertDataClass(dataClass: DataClass) =
        databaseClient.insert()
            .into(DataClass::class.java)
            .using(dataClass)
            .then()
            .then(Mono.just(dataClass))
}
