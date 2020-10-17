package com.alorma.homemenu.time

import java.time.LocalDate

interface Clock {

    suspend fun getToday(): LocalDate
    suspend fun now(): Long
}