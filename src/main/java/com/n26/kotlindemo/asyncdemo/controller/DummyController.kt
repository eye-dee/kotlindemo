package com.n26.kotlindemo.asyncdemo.controller

import com.n26.kotlindemo.asyncdemo.service.DummyService
import com.n26.kotlindemo.pojo.DataClass
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dummy")
class DummyController(
    private val dummyService: DummyService
) {

    @GetMapping
    fun getDummy(): DataClass {
        cpu(10)
        return DataClass("str", 10)
    }

    @GetMapping("send")
    fun makeRequest() = dummyService.makeRequest()

    private fun cpu(milliseconds: Int) {
        val sleepTime = milliseconds * 1000000L // convert to nanoseconds
        val startTime = System.nanoTime()
        while (System.nanoTime() - startTime < sleepTime) {
        }
    }
}
