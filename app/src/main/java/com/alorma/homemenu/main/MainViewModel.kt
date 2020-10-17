package com.alorma.homemenu.main

import androidx.lifecycle.ViewModel
import com.alorma.homemenu.time.Clock
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class MainViewModel(private val clock: Clock) : ViewModel() {

    suspend fun getDays(): List<Day> {
        val today = clock.getToday()

        val min = today.minusDays(6)
        val max = today.plusDays(6)

        return Stream.iterate(min, { t -> t.plusDays(1) })
            .limit(ChronoUnit.DAYS.between(min, max) + 1)
            .map { date ->
                val dayOfWeek = date.dayOfWeek
                val name = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"))
                Day(name, date == today)
            }.collect(Collectors.toList())
    }
}