package com.ufes.automobile.ui.reports

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.ufes.automobile.R
import com.ufes.automobile.data.local.entity.AccidentEntity
import com.ufes.automobile.data.local.entity.MaintenanceEntity
import com.ufes.automobile.domain.repository.AccidentRepository
import com.ufes.automobile.domain.repository.DisplacementRepository
import com.ufes.automobile.domain.repository.InsuranceRepository
import com.ufes.automobile.domain.repository.MaintenanceRepository
import com.ufes.automobile.domain.repository.RechargeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

/**
 * ViewModel class responsible for managing and providing data related to vehicle reports.
 *
 * This class fetches data from various repositories concerning vehicle operations, including
 * displacements, recharges (fueling/charging), maintenances, insurances, and accidents.
 * It processes this data to calculate and expose key statistics and data visualizations
 * suitable for generating reports. The data is formatted to be used directly in charts and
 * UI elements, providing insights into vehicle usage and associated costs over time.
 *
 * **Data Sources:**
 * - `displacementRepository`: Provides access to data about vehicle displacements (distance traveled).
 * - `rechargeRepository`: Provides access to data about vehicle recharges (fueling or charging).
 * - `maintenanceRepository`: Provides access to data about vehicle maintenance records.
 * - `insuranceRepository`: Provides access to data about vehicle insurance records.
 * - `accidentRepository`: Provides access to data about vehicle accident records.
 *
 * **Exposed Data:**
 * - `distanceData`: A `StateFlow` emitting a list of `BarEntry` objects, representing the
 *   distance traveled by the vehicle per month. Suitable for use in a bar chart.
 * - `costData`: A `StateFlow` emitting a list of `PieEntry` objects, representing the
 *   distribution of costs across different categories (recharges, maintenances, insurances).
 *   Suitable for use in a pie chart.
 * - `months`: A `StateFlow` emitting a set of month names (e.g., "Jan", "Feb") for which
 *   data is available within the reporting period (last 6 months).
 * - `totalRechargesCost`: A `StateFlow` emitting the total cost of all recharges for the vehicle.
 * - `totalMaintenancesCost`: A `StateFlow` emitting the total cost of all maintenances for the vehicle.
 * - `totalInsurancesCost`: A `StateFlow` emitting the total cost of all insurances for the vehicle.
 * - `totalAllCost`: A `StateFlow` emitting the total cost of all expenses for the vehicle.
 * - `costPerKm`: A `StateFlow` emitting the cost per kilometer of the vehicle's journey.
 * - `accidents`: A `StateFlow` emitting a list of the most recent accidents associated with the vehicle.
 * - `maintenances`: A `StateFlow` emitting a list of the most recent maintenances associated with the vehicle.
 */
@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val displacementRepository: DisplacementRepository,
    private val rechargeRepository: RechargeRepository,
    private val maintenanceRepository: MaintenanceRepository,
    private val insuranceRepository: InsuranceRepository,
    private val accidentRepository: AccidentRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _distanceData = MutableStateFlow<List<BarEntry>>(emptyList())
    val distanceData: StateFlow<List<BarEntry>> = _distanceData.asStateFlow()

    private val _costData = MutableStateFlow<List<PieEntry>>(emptyList())
    val costData: StateFlow<List<PieEntry>> = _costData.asStateFlow()

    private val _months = MutableStateFlow<Set<String>>(emptySet())
    val months: StateFlow<Set<String>> = _months.asStateFlow()

    private val _totalRechargesCost = MutableStateFlow(0.0f)
    val totalRechargesCost: StateFlow<Float> = _totalRechargesCost.asStateFlow()

    private val _totalMaintenancesCost = MutableStateFlow(0.0f)
    val totalMaintenancesCost: StateFlow<Float> = _totalMaintenancesCost.asStateFlow()

    private val _totalInsurancesCost = MutableStateFlow(0.0f)
    val totalInsurancesCost: StateFlow<Float> = _totalInsurancesCost.asStateFlow()

    private val _totalAllCost = MutableStateFlow(0.0f)
    val totalAllCost: StateFlow<Float> = _totalAllCost.asStateFlow()

    private val _costPerKm = MutableStateFlow(0.0f)
    val costPerKm: StateFlow<Float> = _costPerKm.asStateFlow()

    private val _accidents = MutableStateFlow<List<AccidentEntity>>(emptyList())
    val accidents: StateFlow<List<AccidentEntity>> = _accidents.asStateFlow()

    private val _maintenances = MutableStateFlow<List<MaintenanceEntity>>(emptyList())
    val maintenances: StateFlow<List<MaintenanceEntity>> = _maintenances.asStateFlow()

    fun loadStatistics(vehicleId: Int) {
        viewModelScope.launch {
            var displacements = displacementRepository.getDisplacementsByVehicle(vehicleId)

            var totalDistance = 0f
            displacements.forEach { displacement ->
                totalDistance += displacement.distance
            }

            displacements = displacements.sortedBy { it.date }

            // Calculate the timestamp 6 months ago
            val sixMonthsAgo = Calendar.getInstance()
            sixMonthsAgo.add(Calendar.MONTH, -5)

            // Filter the displacements
            displacements = displacements.filter { displacement ->
                val displacementDate = Calendar.getInstance().apply {
                    time = Date(displacement.date)
                }
                displacementDate.after(sixMonthsAgo) or (displacementDate.get(Calendar.MONTH) == sixMonthsAgo.get(Calendar.MONTH))
            }

            val distancePerMonth = mutableListOf<Float>()
            for (i in 0..11) {
                distancePerMonth.add(i, 0f)
            }

            displacements.forEach { displacement ->
                val month = Calendar.getInstance().apply { time = Date(displacement.date) }.get(Calendar.MONTH)
                distancePerMonth[month] += displacement.distance
            }

            val now = Calendar.getInstance()
            val months = mutableSetOf<String>()
            var j = sixMonthsAgo.get(Calendar.MONTH)
            val u: Int = if (now.get(Calendar.MONTH) + 1 < 0) 11 else now.get(Calendar.MONTH) + 1

            // Use resources to get month names
            while (j != u) {
                val monthName = when (j) {
                    0 -> getApplication<Application>().resources.getString(R.string.jan)
                    1 -> getApplication<Application>().resources.getString(R.string.feb)
                    2 -> getApplication<Application>().resources.getString(R.string.mar)
                    3 -> getApplication<Application>().resources.getString(R.string.apr)
                    4 -> getApplication<Application>().resources.getString(R.string.may)
                    5 -> getApplication<Application>().resources.getString(R.string.jun)
                    6 -> getApplication<Application>().resources.getString(R.string.jul)
                    7 -> getApplication<Application>().resources.getString(R.string.aug)
                    8 -> getApplication<Application>().resources.getString(R.string.sep)
                    9 -> getApplication<Application>().resources.getString(R.string.oct)
                    10 -> getApplication<Application>().resources.getString(R.string.nov)
                    11 -> getApplication<Application>().resources.getString(R.string.dec)
                    else -> ""
                }
                months.add(monthName)
                j++
                if (j > 11) j = 0
            }
            _months.value = months

            val distancePerMonthFiltered = mutableListOf<BarEntry>()
            var i = sixMonthsAgo.get(Calendar.MONTH)
            var k = 0.0f
            val g: Int = if (now.get(Calendar.MONTH) + 1 > 11) 0 else now.get(Calendar.MONTH) + 1

            while (i != g) {
                distancePerMonthFiltered.add(BarEntry(k, distancePerMonth[i]))
                i++
                if (i > 11) i = 0
                k++
            }

            _distanceData.value = distancePerMonthFiltered

            val recharges = rechargeRepository.getRechargesByVehicle(vehicleId)
            var maintenances = maintenanceRepository.getMaintenanceByVehicle(vehicleId)
            val insurances = insuranceRepository.getInsuranceByVehicle(vehicleId)

            var totalRecharges = 0f
            var totalMaintenances = 0f
            var totalInsurances = 0f

            recharges.forEach { recharge ->
                totalRecharges += recharge.cost
            }

            maintenances.forEach { maintenance ->
                totalMaintenances += maintenance.cost
            }

            insurances.forEach { insurance ->
                totalInsurances += insurance.cost
            }

            val totalAll: Float = totalRecharges + totalMaintenances + totalInsurances

            _totalRechargesCost.value = totalRecharges
            _totalMaintenancesCost.value = totalMaintenances
            _totalInsurancesCost.value = totalInsurances
            _totalAllCost.value = totalAll

            if (totalDistance != 0f)
                _costPerKm.value = totalAll / totalDistance
            else
                _costPerKm.value = 0f

            // Use resources to get strings for cost data
            _costData.value = listOf(
                PieEntry(totalRecharges, getApplication<Application>().resources.getString(R.string.fuel_charging)),
                PieEntry(totalMaintenances, getApplication<Application>().resources.getString(R.string.maintenance)),
                PieEntry(totalInsurances, getApplication<Application>().resources.getString(R.string.insurance))
            )

            // Acidentes: últimas 3 ocorrências (ordem cronológica decrescente)
            var accidents = accidentRepository.getAccidentsByVehicle(vehicleId)
            accidents = accidents.sortedByDescending { it.date }
            _accidents.value = accidents.take(3)

            // Manutenções: últimas 3 ocorrências (ordem cronológica decrescente)
            maintenances = maintenanceRepository.getMaintenanceByVehicle(vehicleId)
            maintenances = maintenances.sortedByDescending { it.date }
            _maintenances.value = maintenances.take(3)

            Log.d("ReportsViewModel", "Accidents: ${_accidents.value}")
            Log.d("ReportsViewModel", "Maintenances: ${_maintenances.value}")
        }
    }
}