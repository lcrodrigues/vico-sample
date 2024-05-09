package com.lcrodrigues.vico_sample

import android.graphics.Typeface
import android.text.Spannable
import android.text.style.StyleSpan
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.extension.transformToSpannable
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter

class CustomMarkerLabelFormatter : MarkerLabelFormatter {
    override fun getLabel(
        markedEntries: List<Marker.EntryModel>,
        chartValues: ChartValues,
    ): CharSequence = markedEntries.transformToSpannable { model ->
        val start = length
        append(PATTERN.format(model.entry.y))
        setSpan(StyleSpan(Typeface.BOLD), start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        insert(start, "${model.entry.x.toInt()}\n")
    }

    private companion object {
        const val PATTERN = "%.02f"
    }
}