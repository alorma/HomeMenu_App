package com.alorma.homemenu.main

import java.time.LocalDate

data class Day(
    val name: String,
    val date: LocalDate,
    val isToday: Boolean = false
)