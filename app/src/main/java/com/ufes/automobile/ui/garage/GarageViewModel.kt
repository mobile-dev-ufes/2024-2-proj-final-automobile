package com.ufes.automobile.ui.garage
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
class GarageViewModel @Inject constructor(
    private val garageRepository: GarageRepository
) : ViewModel() {
    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicles: StateFlow<List<Vehicle>> = _vehicles.asStateFlow()

    init {
        loadVehicles()
    }

    private fun loadVehicles() {
        viewModelScope.launch {
            garageRepository.getVehicles().collect { vehiclesList ->
                _vehicles.value = vehiclesList
            }
        }
    }
}