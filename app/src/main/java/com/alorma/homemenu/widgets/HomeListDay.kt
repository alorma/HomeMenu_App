package com.alorma.homemenu.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyRowForIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Ambient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.homemenu.R
import com.alorma.homemenu.main.DateViewModel
import com.alorma.homemenu.main.Day
import com.alorma.homemenu.main.Dish
import com.alorma.homemenu.main.Meal
import com.alorma.homemenu.ui.HomeMenuTheme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Composable
fun daysList(
    days: List<Day>,
    onDayClick: (Day) -> Unit,
) {
    LazyColumnFor(
        items = days,
        contentPadding = PaddingValues(all = 8.dp),
    ) { item ->
        dayRow(day = item, onDayClick = onDayClick)
        dayListSpace()
    }
}

@Composable
private fun dayListSpace() {
    Spacer(modifier = Modifier.preferredHeight(4.dp))
}

@Composable
fun dayRow(
    day: Day,
    onDayClick: (Day) -> Unit,
) {
    val shape = MaterialTheme.shapes.medium

    Row {
        daysNumbers(day)

        val borderColor = if (day.isToday) {
            MaterialTheme.colors.primary
        } else {
            MaterialTheme.colors.onSurface
        }

        Card(
            border = BorderStroke(
                width = 1.dp,
                color = borderColor.copy(
                    alpha = 0.12f
                )
            ),
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier.fillMaxWidth().clip(shape)
        ) {
            Column {
                dayTitle(day, onDayClick)
                dayContent(day)
            }
        }
    }
}

@Composable
fun daysNumbers(day: Day) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
            Text(
                text = day.dateText.day,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.primary.copy(alpha = 0.9f)
            )
            Text(
                text = day.dateText.month,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.primary.copy(alpha = 0.6f)
            )
            Text(
                text = day.dateText.year,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.primary.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun dayTitle(
    day: Day,
    onDayClick: (Day) -> Unit,
) {
    val dayName = if (day.isToday) {
        "Today"
    } else {
        day.name
    }

    val backgroundColor = if (day.isToday) {
        MaterialTheme.colors.primary.copy(alpha = 0.16f)
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.16f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium.copy(
                bottomRight = CornerSize(0.dp),
                bottomLeft = CornerSize(0.dp),
            ))
            .background(backgroundColor)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = dayName,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            asset = vectorResource(id = R.drawable.ic_edit),
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .preferredSize(32.dp)
                .clip(MaterialTheme.shapes.small)
                .clickable(onClick = { onDayClick(day) })
                .padding(8.dp),
        )
    }
}

@Composable
private fun dayContent(day: Day) {
    LazyRowForIndexed(
        items = day.meals,
        modifier = Modifier.padding(
            padding = PaddingValues(
                top = 8.dp,
                bottom = 8.dp,
            )
        ),
    ) { index: Int, meal: Meal ->
        if (index < day.meals.size + 1) {
            Spacer(modifier = Modifier.preferredWidth(8.dp))
        }
        mealContent(meal)
    }
}

@Composable
private fun mealContent(meal: Meal) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {
        Text(
            text = meal.name,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primary.copy(alpha = 0.8f)
        )
        dishesContent(meal)
    }
}

@Composable
private fun dishesContent(
    meal: Meal,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.preferredHeight(8.dp))
        meal.dishes.forEach { dish ->
            dishContent(dish)
            Spacer(modifier = Modifier.preferredHeight(2.dp))
        }
    }
}

@Composable
private fun dishContent(
    dish: Dish,
) {
    ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
        Text(
            text = dish.name,
            style = MaterialTheme.typography.body2,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun dayRowPreview() {
    HomeMenuTheme {
        val date = LocalDate.now()
        val dateViewModel = DateViewModel(
            day = date.dayOfMonth.toString(),
            month = date.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            year = date.year.toString(),
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
                name = stringResource(id = name),
                dishes = dishes
            )
        }

        val day = Day(
            name = "Lunes",
            date = date,
            dateText = dateViewModel,
            meals = meals
        )
        val items = listOf(day, day.copy(isToday = true))

        daysList(days = items, onDayClick = {})
    }
}