package com.n26.kotlindemo.asyncdemo.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
open class WebClientConfig {

    @Bean
    open fun webClient() = WebClient.builder()
        .baseUrl("http://localhost:8080")
        .build()

    @Bean
    open fun objectMapper() {
        val mapper = ObjectMapper().registerModule(KotlinModule())
    }
}
