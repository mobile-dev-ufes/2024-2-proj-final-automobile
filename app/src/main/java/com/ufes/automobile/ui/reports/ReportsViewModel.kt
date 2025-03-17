package com.ufes.automobile.ui.reports

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.ufes.automobile.domain.repository.DisplacementRepository
import com.ufes.automobile.domain.repository.GarageRepository
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

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val displacementRepository: DisplacementRepository,
    private val rechargeRepository: RechargeRepository,
    private val maintenanceRepository: MaintenanceRepository,
    private val insuranceRepository: InsuranceRepository
) : ViewModel() {
    private val _distanceData = MutableStateFlow<List<BarEntry>>(emptyList()) // Corrigido para Entry do MPAndroidChart
    val distanceData: StateFlow<List<BarEntry>> = _distanceData.asStateFlow() // Corrigido para Entry do MPAndroidChart

    private val _costData = MutableStateFlow<List<PieEntry>>(emptyList())
    val costData: StateFlow<List<PieEntry>> = _costData.asStateFlow()

    private val _months = MutableStateFlow<Set<String>>(emptySet())
    val months: StateFlow<Set<String>> = _months.asStateFlow()

    fun loadStatistics(vehicleId: Int) {
        viewModelScope.launch {
            // Simulação de dados (substitua pela lógica real do seu repositório)
            // Exemplo: Distância percorrida por mês
            var displacements = displacementRepository.getDisplacementsByVehicle(vehicleId)
            displacements = displacements.sortedBy { it.date }

            // Calculate the timestamp 6 months ago
            val sixMonthsAgo = Calendar.getInstance()
            sixMonthsAgo.add(Calendar.MONTH, -5) // Subtract 6 months

            // Filter the displacements
            displacements = displacements.filter { displacement ->
                val displacementDate = Calendar.getInstance().apply {
                    time = Date(displacement.date)
                }
                displacementDate.after(sixMonthsAgo) or (displacementDate.get(Calendar.MONTH) == sixMonthsAgo.get(Calendar.MONTH))
            }

            var months = mutableSetOf<String>()
            var distancePerMonth = mutableListOf<Float>()
            for (i in 0..11) {
                distancePerMonth.add(i, 0f)
            }

            displacements.forEach{displacement ->
                val month = Calendar.getInstance().apply { time = Date(displacement.date) }.get(Calendar.MONTH)
                distancePerMonth[month] += displacement.distance
                when (month) {
                    0 -> months.add("Jan")
                    1 -> months.add("Fev")
                    2 -> months.add("Mar")
                    3 -> months.add("Abr")
                    4 -> months.add("Mai")
                    5 -> months.add("Jun")
                    6 -> months.add("Jul")
                    7 -> months.add("Ago")
                    8 -> months.add("Set")
                    9 -> months.add("Out")
                    10 -> months.add("Nov")
                    11 -> months.add("Dez")
                }
            }

            _months.value = months

            var distancePerMonthFiltered = mutableListOf<BarEntry>()
            val now = Calendar.getInstance()
            var i = sixMonthsAgo.get(Calendar.MONTH)
            var k = 0.0f
            var g = 0
            if (now.get(Calendar.MONTH) + 1 > 11)
                g = 0
            else
                g = now.get(Calendar.MONTH) + 1

            while (i != g) {
                distancePerMonthFiltered.add(BarEntry(k, distancePerMonth[i]))
                i++
                if (i > 11)
                    i = 0
                k++
            }

            _distanceData.value = distancePerMonthFiltered

            var recharges = rechargeRepository.getRechargesByVehicle(vehicleId)
            var maintenances = maintenanceRepository.getMaintenanceByVehicle(vehicleId)
            var insurances = insuranceRepository.getInsuranceByVehicle(vehicleId)

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

            _costData.value = listOf(
                PieEntry(totalRecharges, "Fuel/Charging"),
                PieEntry(totalMaintenances, "Maintenance"),
                PieEntry(totalInsurances, "Insurance")
            )
        }
    }
}