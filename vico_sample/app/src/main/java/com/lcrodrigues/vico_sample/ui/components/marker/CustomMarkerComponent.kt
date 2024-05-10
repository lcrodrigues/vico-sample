package com.lcrodrigues.vico_sample.ui.components.marker

import android.graphics.RectF
import com.lcrodrigues.vico_sample.types.ChartType
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.chart.values.ChartValuesProvider
import com.patrykandpatrick.vico.core.component.Component
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.VerticalPosition
import com.patrykandpatrick.vico.core.context.DrawContext
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.extension.ceil
import com.patrykandpatrick.vico.core.extension.doubled
import com.patrykandpatrick.vico.core.extension.half
import com.patrykandpatrick.vico.core.extension.orZero
import com.patrykandpatrick.vico.core.marker.Marker

open class CustomMarkerComponent(
    private val chartType: ChartType,
    label: TextComponent,
    indicator: Component?,
    guideline: LineComponent?
) : MarkerComponent(
    label,
    indicator,
    guideline
) {
    private val tempBounds = RectF()
    private val TextComponent.tickSizeDp: Float
        get() = ((background as? ShapeComponent)?.shape as? MarkerCorneredShape)?.tickSizeDp.orZero

    override fun draw(
        context: DrawContext,
        bounds: RectF,
        markedEntries: List<Marker.EntryModel>,
        chartValuesProvider: ChartValuesProvider,
    ): Unit = with(context) {
        drawGuideline(context, bounds, markedEntries)

        val halfIndicatorSize = indicatorSizeDp.half.pixels
        markedEntries.forEachIndexed { _, model ->
            onApplyEntryColor?.invoke(model.color)
            indicator?.draw(
                context,
                model.location.x - halfIndicatorSize,
                model.location.y - halfIndicatorSize,
                model.location.x + halfIndicatorSize,
                model.location.y + halfIndicatorSize,
            )
        }

        drawLabel(context, bounds, markedEntries, chartValuesProvider.getChartValues(), halfIndicatorSize)
    }

    private fun drawLabel(
        context: DrawContext,
        bounds: RectF,
        markedEntries: List<Marker.EntryModel>,
        chartValues: ChartValues,
        halfIndicatorSize: Float
    ): Unit = with(context) {
        labelFormatter = CustomMarkerLabelFormatter(chartType)
        val text = labelFormatter.getLabel(markedEntries, chartValues)
        val entryX = markedEntries.averageOf { it.location.x }
        val labelBounds =
            label.getTextBounds(context = context, text = text, width = bounds.width().toInt(), outRect = tempBounds)
        val halfOfTextWidth = labelBounds.width().half
        val x = overrideXPositionToFit(entryX, bounds, halfOfTextWidth, halfIndicatorSize)
        this[MarkerCorneredShape.tickXKey] = entryX

        label.drawText(
            context = context,
            text = text,
            textX = x,
            textY = bounds.top,
            verticalPosition = VerticalPosition.Bottom,
            maxTextWidth = minOf(bounds.right - x, x - bounds.left).doubled.ceil.toInt(),
        )
    }

    private fun overrideXPositionToFit(
        xPosition: Float,
        bounds: RectF,
        halfOfTextWidth: Float,
        halfIndicatorSize: Float
    ): Float {
        val endLimit = bounds.right * 0.8

        return when {
            xPosition + halfOfTextWidth > endLimit -> (xPosition - halfIndicatorSize) - halfOfTextWidth
            else -> (xPosition + halfIndicatorSize) + halfOfTextWidth
        }
    }

    private fun drawGuideline(
        context: DrawContext,
        bounds: RectF,
        markedEntries: List<Marker.EntryModel>,
    ) {
        markedEntries
            .map { it.location }
            .forEach { point ->
                guideline?.drawVertical(
                    context,
                    bounds.top,
                    point.y,
                    point.x,
                )
            }
    }

    override fun getInsets(
        context: MeasureContext,
        outInsets: Insets,
        horizontalDimensions: HorizontalDimensions,
    ): Unit = with(context) {
        outInsets.top = label.getHeight(context) + label.tickSizeDp.pixels
    }
}

fun <T> Collection<T>.averageOf(selector: (T) -> Float): Float =
    fold(0f) { sum, element ->
        sum + selector(element)
    } / size