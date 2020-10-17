package com.alorma.homemenu.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.alorma.homemenu.main.Day
import com.alorma.homemenu.ui.HomeMenuTheme

@Composable
fun dayRow(day: Day) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {
        Card(
            shape = MaterialTheme.shapes.medium.copy(
                topLeft = CornerSize(0.dp),
                topRight = CornerSize(0.dp),
            ),
            elevation = 4.dp,
            modifier = Modifier.clickable(onClick = {}),
        ) {
            Column {
                dayTitle(day)
                dayContent(day)
            }
        }
    }
}

@Composable
private fun dayTitle(day: Day) {
    Surface(
        color = if (isSystemInDarkTheme()) {
            MaterialTheme.colors.surface
        } else {
            MaterialTheme.colors.secondary
        },
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        dayRowText(day)
    }
}

@Composable
private fun dayRowText(
    day: Day,
) {
    val emphasis = if (day.isToday) {
        AmbientEmphasisLevels.current.high
    } else {
        AmbientEmphasisLevels.current.medium
    }
    val dayName = if (day.isToday) {
        "Today"
    } else {
        day.name
    }
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(8.dp)
    ) {
        ProvideEmphasis(emphasis = emphasis) {
            Text(
                text = dayName,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
private fun dayContent(day: Day) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Hello")
    }
}

@Preview(showBackground = true)
@Composable
fun dayRowPreview() {
    HomeMenuTheme {
        val day = Day("Lunes")
        Column {
            dayRow(day = day)
            dayRow(day = day.copy(isToday = true))
        }
    }
}