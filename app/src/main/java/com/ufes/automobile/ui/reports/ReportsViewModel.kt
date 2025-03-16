package com.ufes.automobile.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.ufes.automobile.domain.repository.GarageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val garageRepository: GarageRepository // Supondo que você tenha um repositório
) : ViewModel() {
    private val _distanceData = MutableStateFlow<List<Entry>>(emptyList()) // Corrigido para Entry do MPAndroidChart
    val distanceData: StateFlow<List<Entry>> = _distanceData.asStateFlow() // Corrigido para Entry do MPAndroidChart

    private val _costData = MutableStateFlow<List<PieEntry>>(emptyList())
    val costData: StateFlow<List<PieEntry>> = _costData.asStateFlow()

    fun loadStatistics(vehicleId: Int) {
        viewModelScope.launch {
            // Simulação de dados (substitua pela lógica real do seu repositório)
            // Exemplo: Distância percorrida por mês
            _distanceData.value = listOf(
                Entry(1f, 100f), // Janeiro: 100 km
                Entry(2f, 150f), // Fevereiro: 150 km
                Entry(3f, 200f), // Março: 200 km
                Entry(4f, 180f), // Abril: 180 km
            )

            // Exemplo: Distribuição de custos
            _costData.value = listOf(
                PieEntry(300f, "Fuel/Charging"), // 300 unidades em combustível/carga
                PieEntry(150f, "Maintenance"),   // 150 unidades em manutenção
                PieEntry(50f, "Insurance"),      // 50 unidades em seguro
            )
        }
    }
}