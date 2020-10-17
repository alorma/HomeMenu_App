package com.alorma.homemenu.time

interface Clock {

    suspend fun now(): Long
}