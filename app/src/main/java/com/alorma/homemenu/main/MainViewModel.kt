package com.alorma.homemenu.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.homemenu.time.Clock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.math.absoluteValue

@ExperimentalCoroutinesApi
open class MainViewModel(private val clock: Clock) : ViewModel() {

    private val mutateFlow: MutableStateFlow<List<Day>> = MutableStateFlow(emptyList())

    private val daysList = mutableListOf<Day>()

    init {
        viewModelScope.launch {
            val today = clock.getToday()
            onNewDay(today)
        }
    }

    open fun days(): StateFlow<List<Day>> = mutateFlow

    private suspend fun onNewDay(newDay: LocalDate) {
        val today = clock.getToday()
        val dateViewModel = DateViewModel(
            day = newDay.dayOfMonth.toString(),
            month = newDay.month.value.toString(),
            year = newDay.year.toString(),
        )

        val day = Day(
            name = getDayName(date = newDay),
            date = newDay,
            dateText = dateViewModel,
            isToday = newDay == today
        )
        daysList.add(day)
        updateState()
    }

    private fun updateState() {
        mutateFlow.value = daysList.toList().sortedBy { it.date }
    }

    private suspend fun getDayName(date: LocalDate): String {
        return if (clock.getToday() == date) {
            "Today"
        } else {
            val dayOfWeek = date.dayOfWeek
            dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        }
    }

    fun onDayClicked(day: Day) {
        daysList.remove(day)
        updateState()
    }

    fun onAddNewDay() = viewModelScope.launch {
        val today = clock.getToday()
        val random = (-6..6).random().toLong()
        val date = when {
            random < 0 -> today.minusDays(random.absoluteValue)
            random > 0 -> today.plusDays(random.absoluteValue)
            else -> today
        }
        onNewDay(date)
    }
}