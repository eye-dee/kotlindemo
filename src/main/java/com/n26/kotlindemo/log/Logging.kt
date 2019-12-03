package com.n26.kotlindemo.log

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main1() = runBlocking {
    val a = async {
        log("I'm computing a piece of the answer")
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")
}

@Service
class Logging {
    private val log = LoggerFactory.getLogger(Logging::class.java)

    @PostConstruct
    fun function() = runBlocking {
        val a = async {
            log.info("I'm computing a piece of the answer")
            6
        }
        val b = async {
            log.info("I'm computing another piece of the answer")
            7
        }
        log.info("The answer is ${a.await() * b.await()}")


        newSingleThreadContext("C1").use { ctx1 ->
            newSingleThreadContext("C2").use { ctx2 ->
                runBlocking(ctx1) {
                    log.info("Started in ctx1")
                    withContext(ctx2) {
                        log.info("Working in ctx2")
                    }
                    log.info("Back to ctx1")
                }
            }
        }

        log.info("Started main coroutine")
        val v1 = async(CoroutineName("v1co")) {
            delay(500)
            log.info("Computing v1")
            252
        }
        val v2 = async(CoroutineName("v2co")) {
            delay(1000)
            log.info("Computing v2")
            6
        }
        log.info("The answer for v1 / v2 = ${v1.await() / v2.await()}")
    }
}
