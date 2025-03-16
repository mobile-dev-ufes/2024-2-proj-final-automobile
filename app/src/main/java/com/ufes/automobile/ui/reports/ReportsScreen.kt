package com.ufes.automobile.ui.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.utils.ColorTemplate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    vehicleId: Int?,
    navController: NavController,
    viewModel: ReportsViewModel = hiltViewModel()
) {
    LaunchedEffect(vehicleId) {
        vehicleId?.let { viewModel.loadStatistics(it) }
    }

    val distanceData by viewModel.distanceData.collectAsState()
    val costData by viewModel.costData.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Statistics",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Distance Traveled Over Time",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                factory = { context ->
                    LineChart(context).apply {
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)

                        val lineDataSet = LineDataSet(distanceData, "Distance (km)")
                        lineDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
                        lineDataSet.setDrawValues(false)

                        val lineData = LineData(lineDataSet)
                        data = lineData
                        invalidate()
                    }
                },
                update = { chart ->
                    val lineDataSet = LineDataSet(distanceData, "Distance (km)")
                    lineDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
                    lineDataSet.setDrawValues(false)

                    val lineData = LineData(lineDataSet)
                    chart.data = lineData
                    chart.invalidate()
                }
            )

            Text(
                text = "Cost Distribution",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                factory = { context ->
                    PieChart(context).apply {
                        description.isEnabled = false
                        setUsePercentValues(true)
                        isDrawHoleEnabled = true
                        holeRadius = 40f
                        transparentCircleRadius = 45f

                        val pieDataSet = PieDataSet(costData, "Costs")
                        pieDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
                        pieDataSet.setDrawValues(true)

                        val pieData = PieData(pieDataSet)
                        data = pieData
                        invalidate()
                    }
                },
                update = { chart ->
                    val pieDataSet = PieDataSet(costData, "Costs")
                    pieDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
                    pieDataSet.setDrawValues(true)

                    val pieData = PieData(pieDataSet)
                    chart.data = pieData
                    chart.invalidate()
                }
            )
        }
    }
}