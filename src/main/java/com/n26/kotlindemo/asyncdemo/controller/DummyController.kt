package com.n26.kotlindemo.asyncdemo.controller

import com.n26.kotlindemo.asyncdemo.service.DummyService
import com.n26.kotlindemo.pojo.DataClass
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dummy")
class DummyController(
    private val dummyService: DummyService
) {

    private val log = LoggerFactory.getLogger(DummyController::class.java)

    @GetMapping
    suspend fun getDummy(): DataClass {
        delay(1000)
        return DataClass("str", 10)
    }

    @GetMapping("send")
    fun makeRequest() = dummyService.makeRequest()

    @GetMapping("insert")
    fun insert() = dummyService.insertDataToDataClass()
}
