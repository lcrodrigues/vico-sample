package com.lcrodrigues.vico_sample.ui.components.marker

import android.graphics.Typeface
import android.text.Spannable
import android.text.style.StyleSpan
import com.lcrodrigues.vico_sample.getDaysOfWeek
import com.lcrodrigues.vico_sample.getFormattedCurrency
import com.lcrodrigues.vico_sample.getWeeksInMonth
import com.lcrodrigues.vico_sample.hoursOfDay
import com.lcrodrigues.vico_sample.monthsOfYear
import com.lcrodrigues.vico_sample.types.ChartType
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.extension.transformToSpannable
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter
import java.time.Month
import java.time.Year

class CustomMarkerLabelFormatter(private val chartType: ChartType) : MarkerLabelFormatter {
    override fun getLabel(
        markedEntries: List<Marker.EntryModel>,
        chartValues: ChartValues,
    ): CharSequence = markedEntries.transformToSpannable { model ->
        val start = length
        append(getYAxisText(model.entry.y))

        setSpan(StyleSpan(Typeface.BOLD), start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        insert(start, "${getXAxisText(model.entry.x.toInt())}\n")
    }

    private fun getXAxisText(x: Int): String {
        return when (chartType) {
            ChartType.DAILY -> hoursOfDay[x]
            ChartType.WEEKLY -> {
                val date = getDaysOfWeek(2, Month.JANUARY)[x]
                "${date.second}. ${date.first}"
            }
            ChartType.MONTHLY -> getWeeksInMonth(Month.JANUARY.value, Year.now().value)[x]
            ChartType.YEARLY -> monthsOfYear[x]
        }
    }

    private fun getYAxisText(y: Float) =
        getFormattedCurrency(y)
}