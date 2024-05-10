package com.lcrodrigues.vico_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lcrodrigues.vico_sample.ui.components.charts.DailyChart
import com.lcrodrigues.vico_sample.ui.components.charts.MonthlyChart
import com.lcrodrigues.vico_sample.ui.components.charts.WeeklyChart
import com.lcrodrigues.vico_sample.ui.components.charts.YearlyChart
import com.lcrodrigues.vico_sample.ui.theme.Vico_sampleTheme
import com.lcrodrigues.vico_sample.ui.viewmodel.ChartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: ChartViewModel = viewModel()
            Vico_sampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                    ) {
                        DailyChart(viewModel.dailyChartEntryModelProducer)
                        WeeklyChart(viewModel.weeklyChartEntryModelProducer)
                        MonthlyChart(viewModel.monthlyChartEntryModelProducer)
                        YearlyChart(viewModel.yearlyChartEntryModelProducer)
                    }
                }
            }
        }
    }
}