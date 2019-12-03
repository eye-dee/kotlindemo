package com.n26.kotlindemo.threads

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main4() {
    repeat(100) {
        Thread {
            Thread.sleep(100000)
            println("#$it is here")
        }.start()
    }
}

fun main3() = runBlocking {
    repeat(1000000) {
        launch {
            delay(100000)
            println("#$it is here")
        }
    }
}

fun main5() = runBlocking {
    repeat(1000000) {
        launch(Dispatchers.IO) {
            delay(100000)
            println("#$it is here")
        }
    }

//    delay(15000)
//
//    repeat(1000000) {
//        launch(Dispatchers.IO) {
//            delay(100000)
//            println("#$it is here")
//        }
//    }
}


