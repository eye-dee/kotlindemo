package com.n26.kotlindemo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KotlindemoApplication {
}

fun main(args: Array<String>) {
    System.setProperty("reactor.netty.ioWorkerCount", "100");
    SpringApplication.run(KotlindemoApplication::class.java, *args)
}
