package com.alorma.homemenu.widgets

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.homemenu.R
import com.alorma.homemenu.main.DateViewModel
import com.alorma.homemenu.main.Day
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
    Divider(
        color = MaterialTheme.colors.onBackground.copy(alpha = 0.08f),
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    )
    Spacer(modifier = Modifier.preferredHeight(4.dp))
}

@Composable
fun dayRow(
    day: Day,
    onDayClick: (Day) -> Unit,
) {
    val shape = MaterialTheme.shapes.medium

    val backgroundColor = if (day.isToday) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.surface
    }
    val titleColor = if (day.isToday) {
        MaterialTheme.colors.onPrimary
    } else {
        MaterialTheme.colors.onSurface
    }

    Row {
        daysNumbers(day)
        Surface(
            color = backgroundColor,
            shape = shape,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .clip(shape)
                    .clickable(onClick = { onDayClick(day) })
                    .padding(8.dp)
            ) {
                dayTitle(day, titleColor)
                dayContent()
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
    titleColor: Color,
) {
    val dayName = if (day.isToday) {
        "Today"
    } else {
        day.name
    }
    Text(
        text = dayName,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.h6,
        color = titleColor
    )
}

@Composable
private fun dayContent() {
    Column {
        Text(
            text = stringResource(id = R.string.day_row_breakfast_title),
            style = MaterialTheme.typography.subtitle2,
        )
        Text(
            text = stringResource(id = R.string.day_row_lunch_title),
            style = MaterialTheme.typography.subtitle2,
        )
        Text(
            text = stringResource(id = R.string.day_row_dunner_title),
            style = MaterialTheme.typography.subtitle2,
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
        val day = Day(
            name = "Lunes",
            date = date,
            dateText = dateViewModel
        )
        val items = listOf(day, day.copy(isToday = true))

        daysList(days = items, onDayClick = {})
    }
}