package com.alorma.homemenu.widgets

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.homemenu.main.Day
import com.alorma.homemenu.ui.HomeMenuTheme

@Composable
fun daysList(
    days: List<Day>,
    onDayClick: (Day) -> Unit,
) {
    days.forEachIndexed { index, day ->
        dayRow(day, onDayClick)
        dayListSpace(index, days)
    }
}

@Composable
private fun dayListSpace(
    index: Int,
    days: List<Day>
) {
    if (index < days.size - 1) {
        Spacer(modifier = Modifier.preferredHeight(4.dp))
        Divider(
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.08f),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
        Spacer(modifier = Modifier.preferredHeight(4.dp))
    }
}

@Composable
fun dayRow(
    day: Day,
    onDayClick: (Day) -> Unit
) {
    val shape = MaterialTheme.shapes.medium
    val backgroundColor = if (day.isToday) {
        MaterialTheme.colors.primary.copy(alpha = 0.08f)
    } else {
        MaterialTheme.colors.background
    }
    Column(
        modifier = Modifier.fillMaxWidth()
            .clip(shape)
            .background(color = backgroundColor)
            .clickable(onClick = { onDayClick(day) })
            .padding(8.dp),
    ) {
        dayTitle(day)
        dayContent(day)
    }
}

@Composable
private fun dayTitle(day: Day) {
    dayRowText(day)
}

@Composable
private fun dayRowText(
    day: Day,
) {
    val emphasis = if (day.isToday) {
        FontWeight.Bold
    } else {
        FontWeight.Normal
    }
    val dayName = if (day.isToday) {
        "Today"
    } else {
        day.name
    }
    Text(
        text = dayName,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.subtitle1.copy(
            fontWeight = emphasis,
        ),
        color = MaterialTheme.colors.primary
    )
}

@Composable
private fun dayContent(day: Day) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Hello",
            style = MaterialTheme.typography.body2,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun dayRowPreview() {
    HomeMenuTheme {
        val day = Day("Lunes")
        Column {
            daysList(
                days = listOf(
                    day, day.copy(isToday = true)
                )
            ) { }
        }
    }
}