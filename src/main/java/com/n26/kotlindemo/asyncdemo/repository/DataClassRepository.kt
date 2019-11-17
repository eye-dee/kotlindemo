package com.n26.kotlindemo.asyncdemo.repository

import com.n26.kotlindemo.pojo.DataClass
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class DataClassRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun insertDataClass(dataClass: DataClass): DataClass {
        jdbcTemplate.execute(
            "INSERT INTO data_class(str, int) VALUES('${dataClass.str}', ${dataClass.int})"
        )
        return dataClass
    }
}
