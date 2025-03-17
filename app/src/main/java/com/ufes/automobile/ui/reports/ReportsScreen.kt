package com.ufes.automobile.ui.reports

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.ufes.automobile.ui.theme.AutoMobileTheme

@Composable
fun ReportsScreen(
    vehicleId: Int?,
    navController: NavController,
    viewModel: ReportsViewModel = hiltViewModel()
) {
    LaunchedEffect(vehicleId) {
        vehicleId?.let { viewModel.loadStatistics(it) }
    }

    val distanceData = viewModel.distanceData.collectAsState().value
    val costData = viewModel.costData.collectAsState().value
    val months = viewModel.months.collectAsState().value

    ReportsContent(
        onBackClick = { navController.popBackStack() },
        distanceData = distanceData,
        costData = costData,
        months = months
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsContent(
    onBackClick: () -> Unit,
    distanceData: List<BarEntry>,
    costData: List<PieEntry>,
    months: Set<String>,
){
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
                    IconButton(onClick = onBackClick) {
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
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Distance Traveled Monthly (km)",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        factory = { context ->
                            BarChart(context).apply {
                                description.isEnabled = false // Remover a descrição
                                legend.isEnabled = false
                                //setTouchEnabled(true) // Permitir interação com o usuário
                                //isDragEnabled = true // Permitir arrastar o gráfico
                                //setScaleEnabled(true) // Permitir zoom
                                //setPinchZoom(true) // Permitir zoom com dois dedos
                                // Outras configurações do gráfico podem vir aqui
                                val barDataSet = BarDataSet(distanceData, "Distância (km)") // Rótulo do conjunto de dados

                                barDataSet.color = ContextCompat.getColor(context, android.R.color.holo_blue_dark) // Cor do conjunto de dados

                                xAxis.textSize = 14f
                                xAxis.setDrawGridLines(false)
                                xAxis.position = XAxis.XAxisPosition.BOTTOM

                                val barData = BarData(barDataSet)
                                data = barData

                                animateY(1000) // Animação de entrada

                                invalidate() // Atualizar o gráfico
                            }
                        },
                        update = { chart ->
                            val barDataSet = BarDataSet(distanceData, "Distância (km)")

                            val xAxisVals = mutableListOf<String>()
                            months.forEachIndexed { index, value ->
                                xAxisVals.add(index, value)
                            }

                            val xAxisFormatter = IndexAxisValueFormatter(xAxisVals)
                            chart.xAxis.valueFormatter = xAxisFormatter

                            val barData = BarData(barDataSet)
                            chart.data = barData
                            chart.invalidate()
                        }
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
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

                                legend.isEnabled = true // Garante que a legenda esteja visível
                                legend.textSize = 14f

                                val pieDataSet = PieDataSet(costData, "Costs")
                                pieDataSet.colors = ColorTemplate.LIBERTY_COLORS.toList()
                                pieDataSet.setDrawValues(false)
                                setDrawEntryLabels(false)

                                val pieData = PieData(pieDataSet)
                                data = pieData
                                invalidate()
                            }
                        },
                        update = { chart ->
                            chart.setDrawEntryLabels(false)
                            chart.legend.isEnabled = true // Garante que a legenda esteja visível
                            chart.legend.textSize = 14f
                            val pieDataSet = PieDataSet(costData, "Costs")
                            pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
                            pieDataSet.setDrawValues(false)

                            val pieData = PieData(pieDataSet)
                            chart.data = pieData
                            chart.invalidate()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReportsScreenPreview() {
    val distanceData = listOf(
        BarEntry(0f, 100f), // Janeiro: 100 km
        BarEntry(1f, 150f), // Fevereiro: 150 km
        BarEntry(2f, 200f), // Março: 200 km
        BarEntry(3f, 180f), // Abril: 180 km
        BarEntry(4f, 220f), // Maio: 220 km
        BarEntry(5f, 250f), // Junho: 250 km
    )
    val costData = listOf(
        PieEntry(300f, "Fuel/Charging"), // 300 unidades em combustível/carga
        PieEntry(150f, "Maintenance"),   // 150 unidades em manutenção
        PieEntry(50f, "Insurance"),      // 50 unidades em seguro
    )
    val months = setOf("Jan", "Fev", "Mar", "Abr", "Mai", "Jun")

    AutoMobileTheme {
        ReportsContent(
            onBackClick = {},
            distanceData = distanceData,
            costData = costData,
            months = months
        )
    }
}