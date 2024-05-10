package com.lcrodrigues.vico_sample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.util.RandomEntriesGenerator
import kotlinx.coroutines.launch

private const val DAILY_X_RANGE_TOP = 23
private const val DAILY_Y_RANGE_TOP = 1

private const val WEEKLY_X_RANGE_TOP = 6
private const val WEEKLY_Y_RANGE_TOP = 10

private const val MONTHLY_X_RANGE_TOP = 4
private const val MONTHLY_Y_RANGE_TOP = 5

private const val YEARLY_X_RANGE_TOP = 11
private const val YEARLY_Y_RANGE_TOP = 100

class ChartViewModel : ViewModel() {
    private val dailyRandomGenerator = RandomEntriesGenerator(
        xRange = 0..DAILY_X_RANGE_TOP,
        yRange = 0..DAILY_Y_RANGE_TOP
    )

    private val weeklyRandomGenerator = RandomEntriesGenerator(
        xRange = 0..WEEKLY_X_RANGE_TOP,
        yRange = 0..WEEKLY_Y_RANGE_TOP
    )

    private val monthlyRandomGenerator = RandomEntriesGenerator(
        xRange = 0..MONTHLY_X_RANGE_TOP,
        yRange = 0..MONTHLY_Y_RANGE_TOP
    )

    private val yearlyRandomGenerator = RandomEntriesGenerator(
        xRange = 0..YEARLY_X_RANGE_TOP,
        yRange = 0..YEARLY_Y_RANGE_TOP
    )

    val dailyChartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()
    val weeklyChartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()
    val monthlyChartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()
    val yearlyChartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()

    init {
        viewModelScope.launch {
            dailyChartEntryModelProducer.setEntries(
                dailyRandomGenerator.generateRandomEntries()
            )

            weeklyChartEntryModelProducer.setEntries(
                weeklyRandomGenerator.generateRandomEntries()
            )

            monthlyChartEntryModelProducer.setEntries(
                monthlyRandomGenerator.generateRandomEntries()
            )

            yearlyChartEntryModelProducer.setEntries(
                yearlyRandomGenerator.generateRandomEntries()
            )
        }
    }
}