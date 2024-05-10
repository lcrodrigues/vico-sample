package com.lcrodrigues.vico_sample.ui.components.marker

import android.graphics.Typeface
import android.text.Layout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.lcrodrigues.vico_sample.types.ChartType
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.overlayingComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.marker.Marker

@Composable
internal fun rememberMarker(chartType: ChartType): Marker {
    val labelBackgroundColor = Color(0xFF2A7C00)
    val labelBackground = remember(labelBackgroundColor) {
        ShapeComponent(labelBackgroundShape, labelBackgroundColor.toArgb())
    }

    val label = textComponent(
        color = Color.White,
        background = labelBackground,
        lineCount = LABEL_LINE_COUNT,
        padding = labelPadding,
        typeface = Typeface.MONOSPACE,
        textAlignment = Layout.Alignment.ALIGN_CENTER
    )

    val indicatorInnerComponent = shapeComponent(Shapes.pillShape, Color(0xFFEEFFE6))
    val indicatorCenterComponent = shapeComponent(Shapes.pillShape, Color.Transparent)
    val indicatorOuterComponent = shapeComponent(Shapes.pillShape, Color.Transparent)

    val indicator = overlayingComponent(
        outer = indicatorOuterComponent,
        inner = overlayingComponent(
            outer = indicatorCenterComponent,
            inner = indicatorInnerComponent,
            innerPaddingAll = indicatorInnerAndCenterComponentPaddingValue,
        ),
        innerPaddingAll = indicatorCenterAndOuterComponentPaddingValue,
    )
    val guideline = lineComponent(
        Color(0xFF2A7C00),
        guidelineThickness
    )

    return remember(label, indicator, guideline) {
        object : CustomMarkerComponent(chartType, label, indicator, guideline) {
            init {
                indicatorSizeDp = INDICATOR_SIZE_DP
                onApplyEntryColor = { _ ->
                    with(indicatorCenterComponent) {
                        color = Color(0xFF226400).hashCode()
                    }
                }
            }
        }
    }
}

private const val LABEL_LINE_COUNT = 2
private const val INDICATOR_SIZE_DP = 28f

private val labelBackgroundShape = Shapes.roundedCornerShape(16)
private val labelHorizontalPaddingValue = 8.dp
private val labelVerticalPaddingValue = 4.dp
private val labelPadding = dimensionsOf(labelHorizontalPaddingValue, labelVerticalPaddingValue)
private val indicatorInnerAndCenterComponentPaddingValue = 3.dp
private val indicatorCenterAndOuterComponentPaddingValue = 7.dp
private val guidelineThickness = 1.dp
