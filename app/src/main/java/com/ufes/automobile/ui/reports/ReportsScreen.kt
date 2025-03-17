package com.ufes.automobile.ui.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
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
import com.ufes.automobile.R
import com.ufes.automobile.data.local.entity.AccidentEntity
import com.ufes.automobile.data.local.entity.MaintenanceEntity
import com.ufes.automobile.ui.common.AccidentCard
import com.ufes.automobile.ui.common.MaintenanceCard
import com.ufes.automobile.ui.theme.AutoMobileTheme

/**
 * Displays the reports screen for a specific vehicle.
 *
 * This composable function fetches and displays various statistics and reports related to a given vehicle.
 * It utilizes a [ReportsViewModel] to retrieve data and updates the UI accordingly.
 *
 * @param vehicleId The ID of the vehicle for which to display reports. If null, no data will be loaded.
 * @param navController The [NavController] used for navigating back from the reports screen.
 * @param viewModel The [ReportsViewModel] responsible for managing and providing the report data.
 *                  Defaults to a [hiltViewModel] instance.
 *
 * The screen displays the following information:
 *  - Distance monthly data: Statistics related to the distance monthly traveled.
 *  - Cost distribution: Statistics related to the costs incurred.
 *  - Cost per kilometer: The calculated cost per kilometer driven.
 *  - Total recharges cost: The sum of all recharge costs.
 *  - Total maintenances cost: The sum of all maintenance costs.
 *  - Total insurances cost: The sum of all insurance costs.
 *  - Total all cost: The total cost across all categories.
 *  - Accidents: List of recent accidents of the vehicle.
 *  - Maintenances: List of recent maintenances of the vehicle.
 *
 * The screen also provides a back button to return to the previous screen.
 */
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
    val totalRechargesCost = viewModel.totalRechargesCost.collectAsState().value
    val totalMaintenancesCost = viewModel.totalMaintenancesCost.collectAsState().value
    val totalInsurancesCost = viewModel.totalInsurancesCost.collectAsState().value
    val totalAllCost = viewModel.totalAllCost.collectAsState().value
    val costPerKm = viewModel.costPerKm.collectAsState().value
    val accidents = viewModel.accidents.collectAsState().value
    val maintenances = viewModel.maintenances.collectAsState().value

    ReportsContent(
        onBackClick = { navController.popBackStack() },
        distanceData = distanceData,
        costData = costData,
        months = months,
        totalRechargesCost = totalRechargesCost,
        totalMaintenancesCost = totalMaintenancesCost,
        totalInsurancesCost = totalInsurancesCost,
        totalAllCost = totalAllCost,
        costPerKm = costPerKm,
        accidents = accidents,
        maintenances = maintenances,
        distanceKmLabel = stringResource(id = R.string.distance)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsContent(
    onBackClick: () -> Unit,
    distanceData: List<BarEntry>,
    costData: List<PieEntry>,
    months: Set<String>,
    totalRechargesCost: Float = 0f,
    totalMaintenancesCost: Float = 0f,
    totalInsurancesCost: Float = 0f,
    totalAllCost: Float = 0f,
    costPerKm: Float = 0f,
    accidents: List<AccidentEntity> = emptyList(),
    maintenances: List<MaintenanceEntity> = emptyList(),
    distanceKmLabel: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.statistics),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
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
                            text = stringResource(id = R.string.distance_traveled),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        AndroidView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            factory = { context ->
                                BarChart(context).apply {
                                    description.isEnabled = false
                                    legend.isEnabled = false
                                    val barDataSet = BarDataSet(distanceData, distanceKmLabel)

                                    barDataSet.color = ContextCompat.getColor(
                                        context,
                                        android.R.color.holo_blue_dark
                                    )

                                    xAxis.textSize = 14f
                                    xAxis.setDrawGridLines(false)
                                    xAxis.position = XAxis.XAxisPosition.BOTTOM

                                    val barData = BarData(barDataSet)
                                    data = barData

                                    animateY(1000)
                                    invalidate()
                                }
                            },
                            update = { chart ->
                                val barDataSet = BarDataSet(distanceData, distanceKmLabel)

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
            }
            item {
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
                            text = stringResource(id = R.string.cost_distribution),
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

                                    legend.isEnabled = true
                                    legend.textSize = 14f

                                    val pieDataSet = PieDataSet(costData, "")
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
                                chart.description.isEnabled = false
                                chart.legend.isEnabled = true
                                chart.legend.textSize = 14f
                                val pieDataSet = PieDataSet(costData, "")
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
            item {
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(id = R.string.cost_per_km),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = String.format("$%.2f", costPerKm),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
            item {
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
                    Text(
                        modifier = Modifier.padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(id = R.string.total_costs),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp)
                                .widthIn(max = 200.dp),
                            text = stringResource(id = R.string.fuel_charging),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier.padding(24.dp, 16.dp),
                            text = "\$${totalRechargesCost}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp)
                                .widthIn(max = 200.dp),
                            text = stringResource(id = R.string.maintenance),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier.padding(24.dp, 16.dp),
                            text = "\$${totalMaintenancesCost}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp)
                                .widthIn(max = 200.dp),
                            text = stringResource(id = R.string.insurance),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier.padding(24.dp, 16.dp),
                            text = "\$${totalInsurancesCost}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp)
                                .widthIn(max = 200.dp),
                            text = stringResource(id = R.string.all),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier.padding(24.dp, 16.dp),
                            text = "\$${totalAllCost}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
            item {
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
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(0.dp, 16.dp),
                        text = stringResource(id = R.string.recent_maintenances),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            items(maintenances) { maintenance ->
                MaintenanceCard(
                    maintenance = maintenance,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
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
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(0.dp, 16.dp),
                        text = stringResource(id = R.string.recent_accidents),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            items(accidents) { accident ->
                AccidentCard(
                    accident = accident,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReportsScreenPreview() {
    val distanceData = listOf(
        BarEntry(0f, 100f),
        BarEntry(1f, 150f),
        BarEntry(2f, 200f),
        BarEntry(3f, 180f),
        BarEntry(4f, 220f),
        BarEntry(5f, 250f)
    )
    val costData = listOf(
        PieEntry(300f, "Fuel/Charging"),
        PieEntry(150f, "Maintenance"),
        PieEntry(50f, "Insurance")
    )
    val months = setOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")

    AutoMobileTheme {
        ReportsContent(
            onBackClick = {},
            distanceData = distanceData,
            costData = costData,
            months = months,
            totalRechargesCost = 100f,
            totalMaintenancesCost = 200f,
            totalInsurancesCost = 300f,
            totalAllCost = 600f,
            costPerKm = 100f,
            accidents = emptyList(),
            maintenances = emptyList(),
            distanceKmLabel = "Distance (km)"
        )
    }
}