package com.alorma.homemenu.time

import java.time.LocalDateTime
import java.time.ZoneId

class NativeClock : Clock {
    override suspend fun now(): Long = LocalDateTime.now()
        .atZone(
            ZoneId.systemDefault()
        )
        .toInstant()
        .toEpochMilli()
}