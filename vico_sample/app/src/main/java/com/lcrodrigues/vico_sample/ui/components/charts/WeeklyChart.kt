package com.lcrodrigues.vico_sample.ui.components.charts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lcrodrigues.vico_sample.getDaysOfWeek
import com.lcrodrigues.vico_sample.getFormattedCurrency
import com.lcrodrigues.vico_sample.types.ChartType
import com.lcrodrigues.vico_sample.ui.components.marker.rememberMarker
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

val formattedDaysOfWeek = getDaysOfWeek(1)

private val bottomAxisValueFormatter =
    AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _ -> formattedDaysOfWeek[x.toInt() % formattedDaysOfWeek.size].second }

private val startAxisValueFormatter =
    AxisValueFormatter<AxisPosition.Vertical.Start> { value, chartValues ->
        if (value == chartValues.maxY) {
            getFormattedCurrency(value)
        } else {
            String.format("%.2f", value)
        }
    }

private val bottomAxisItemPlacer = AxisItemPlacer.Horizontal.default(
    addExtremeLabelPadding = true
)

@Composable
fun WeeklyChart(producer: ChartEntryModelProducer) {
    Column {
        Text("Weekly Chart", modifier = Modifier.padding(top = 16.dp, start = 16.dp))
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).padding(end = 16.dp)) {
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
                startAxis = rememberStartAxis(
                    valueFormatter = startAxisValueFormatter,
                    itemPlacer = AxisItemPlacer.Vertical.default(3)
                ),
                bottomAxis = rememberBottomAxis(
                    guideline = null,
                    itemPlacer = bottomAxisItemPlacer,
                    valueFormatter = bottomAxisValueFormatter
                ),
                marker = rememberMarker(ChartType.WEEKLY),
                runInitialAnimation = false,
                horizontalLayout = HorizontalLayout.fullWidth(),
                isZoomEnabled = false,
                chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false)
            )
        }
    }
}