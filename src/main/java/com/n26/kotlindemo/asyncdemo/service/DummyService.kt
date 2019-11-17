package com.n26.kotlindemo.asyncdemo.service

import com.n26.kotlindemo.pojo.DataClass
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class DummyService(
    private val webClient: WebClient
) {

    private val log = LoggerFactory.getLogger(DummyService::class.java)

    fun makeRequest() =
        webClient.get()
            .uri("api/dummy")
            .exchange()
            .flatMap { it.toEntity(DataClass::class.java) }

}
