package com.ufes.automobile.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufes.automobile.domain.model.Vehicle
import com.ufes.automobile.domain.repository.GarageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val garageRepository: GarageRepository
) : ViewModel() {
    private val _vehicle = MutableStateFlow<Vehicle?>(null)
    val vehicle: StateFlow<Vehicle?> = _vehicle.asStateFlow()

    fun loadVehicle(vehicleId: Int) {
        viewModelScope.launch {
            val vehicleEntity = garageRepository.getVehicleById(vehicleId)
            _vehicle.value = vehicleEntity
        }
    }
}