package com.lcrodrigues.vico_sample

import java.text.NumberFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.Locale

val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.CANADA)
val hoursOfDay = Array(24) { i -> "%02d:00".format(i) }

val daysOfWeek: List<Pair<Number, String>> =
    DayOfWeek.entries.toTypedArray().mapIndexed { index, dayOfWeek ->
        Pair(index + 1, dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() })
    }

val monthsOfYear: List<String> = Month.entries.map { month ->
    month
        .toString()
        .slice(0..2)
        .lowercase()
        .replaceFirstChar {
            it.uppercase()
        }
}

fun getDaysOfWeek(sliceIn: Int = 0, month: Month? = null): List<Pair<String, String>> {
    return if (sliceIn > 0) {
        if (month != null) {
            daysOfWeek.map { pair ->
                val formatFirst = "${
                    month.name.slice(0..2).lowercase().replaceFirstChar { it.uppercase() }
                } ${pair.first}"
                val formatSecond = pair.second.slice(0..sliceIn)
                Pair(formatFirst, formatSecond)
            }
        } else {
            daysOfWeek.map {
                val formatSecond = it.second.slice(0..sliceIn)
                Pair(it.first.toString(), formatSecond)
            }
        }
    } else {
        daysOfWeek.map {
            Pair(it.toString(), it.second)
        }
    }
}

fun getWeeksInMonth(month: Int, year: Int): Array<String> {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1)
    val firstWeekFormatter = DateTimeFormatter.ofPattern("MMM d")
    val formatter = DateTimeFormatter.ofPattern("d")

    val weeks = mutableListOf<String>()
    var startOfWeek = firstDayOfMonth

    while (startOfWeek.isBefore(lastDayOfMonth)) {
        val endOfWeek = if (startOfWeek.plusDays(6).isBefore(lastDayOfMonth))
            startOfWeek.plusDays(6)
        else
            lastDayOfMonth

        val weekString = "${
            if (startOfWeek == firstDayOfMonth) {
                startOfWeek.format(firstWeekFormatter)
            } else {
                startOfWeek.format(
                    formatter
                )
            }
        }-${endOfWeek.format(formatter)}"
        weeks.add(weekString)

        startOfWeek = endOfWeek.plusDays(1)
    }

    return weeks.toTypedArray()
}

fun getFormattedCurrency(value: Float) =
    "CA${currencyFormat.format(value.toDouble())}"
