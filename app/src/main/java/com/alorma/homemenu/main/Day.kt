package com.alorma.homemenu.main

import java.time.LocalDate

data class Day(
    val name: String,
    val date: LocalDate,
    val dateText: DateViewModel,
    val isToday: Boolean = false,
    val meals: List<Meal>,
)

data class DateViewModel(val day: String, val month: String, val year: String)

data class Meal(val name: String, val dishes: List<Dish>)

data class Dish(val name: String)