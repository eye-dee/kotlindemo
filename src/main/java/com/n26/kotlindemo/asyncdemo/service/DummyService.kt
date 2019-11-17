package com.n26.kotlindemo.asyncdemo.service

import com.n26.kotlindemo.pojo.DataClass
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DummyService(
    private val restTemplate: RestTemplate
) {

    private val log = LoggerFactory.getLogger(DummyService::class.java)

    fun makeRequest() =
        restTemplate.getForEntity("http://localhost:8080", DataClass::class.java)
            .body

}
