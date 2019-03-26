package com.veenapilli.learningthroughoxford.features.firebase

import java.util.*
import java.util.concurrent.atomic.AtomicInteger


object NotificationId {
    private val randomInt = Random()
    private val c = AtomicInteger(randomInt.nextInt())
    val id: Int
        get() = c.incrementAndGet()
}
