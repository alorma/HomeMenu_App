package com.alorma.homemenu.main

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.homemenu.R
import com.alorma.homemenu.base.StringsProvider
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
open class MainViewModel(
    private val clock: Clock,
    private val stringsProvider: StringsProvider
) : ViewModel() {

    private val mutateFlow: MutableStateFlow<List<Day>> = MutableStateFlow(emptyList())

    private val daysList = mutableListOf<Day>()

    init {
        viewModelScope.launch {
            val today = clock.getToday()
            onNewDay(today)
            val tomorrow = today.plusDays(1)
            onNewDay(tomorrow)
        }
    }

    open fun days(): StateFlow<List<Day>> = mutateFlow

    private suspend fun onNewDay(newDay: LocalDate) {
        val today = clock.getToday()
        val dateViewModel = DateViewModel(
            day = newDay.dayOfMonth.toString(),
            month = newDay.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            year = newDay.year.toString(),
        )

        val dishes = listOf(
            Dish("Mac & Cheese"),
            Dish("Heura"),
            Dish("Yogurt")
        )

        val meals = listOf(
            R.string.day_row_breakfast_title,
            R.string.day_row_lunch_title,
            R.string.day_row_dunner_title,
        ).map { name ->
            Meal(
                name = stringsProvider.getString(name),
                dishes = dishes
            )
        }

        val day = Day(
            name = getDayName(date = newDay),
            date = newDay,
            dateText = dateViewModel,
            isToday = newDay == today,
            meals = meals
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