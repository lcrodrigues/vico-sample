package com.lcrodrigues.vico_sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlin.random.Random

private const val X_RANGE_TOP = 7
private const val Y_RANGE_TOP = 5
private const val BOTTOM_AXIS_ITEM_SPACING = 3
private const val BOTTOM_AXIS_ITEM_OFFSET = 1

private val bottomAxisItemPlacer = if (X_RANGE_TOP > 10) {
    AxisItemPlacer.Horizontal.default(
        BOTTOM_AXIS_ITEM_SPACING,
        BOTTOM_AXIS_ITEM_OFFSET,
        addExtremeLabelPadding = true
    )
} else {
    AxisItemPlacer.Horizontal.default(
        addExtremeLabelPadding = true
    )
}

@Composable
fun VicoChart() {
    val producer = ChartEntryModelProducer()
    producer.setEntries(generateRandomEntries())

    Box(modifier = Modifier.fillMaxWidth().height(200.dp).padding(top = 32.dp)) {
        ProvideChartStyle {
            val defaultColumns = currentChartStyle.columnChart.columns
            Chart(
                chart = columnChart(
                    columns = remember(defaultColumns) {
                        defaultColumns.map { _ ->
                            LineComponent(Color(0xFF43AE0C).hashCode(), 24f)
                        }
                    },
                ),
                chartModelProducer = producer,
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(
                    guideline = null,
                    itemPlacer = bottomAxisItemPlacer
                ),
                marker = rememberMarker(),
                runInitialAnimation = false,
                horizontalLayout = HorizontalLayout.fullWidth(),
                isZoomEnabled = false,
                chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false)
            )
        }
    }
}

fun generateRandomEntries(): List<FloatEntry> {
    val xRange: IntProgression = 0..X_RANGE_TOP
    val yRange: IntProgression = 0..Y_RANGE_TOP

    val result = ArrayList<FloatEntry>()
    val yLength = yRange.last - yRange.first
    for (x in xRange) {
        result += entryOf(x.toFloat(), yRange.first + Random.nextFloat() * yLength)
    }
    return result
}