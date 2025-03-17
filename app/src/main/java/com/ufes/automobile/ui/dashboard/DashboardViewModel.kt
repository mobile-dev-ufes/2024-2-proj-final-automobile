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

/**
- * ViewModel for the Dashboard screen.
+ * [DashboardViewModel] manages the data and business logic for the Dashboard screen.
 *
- * This ViewModel is responsible for fetching and exposing data related to a specific vehicle
- * to the UI. It interacts with the [GarageRepository] to retrieve the vehicle information.
+ * This ViewModel is responsible for:
+ *  - Fetching and exposing data related to a specific vehicle to the UI.
+ *  - Interacting with the [GarageRepository] to retrieve the vehicle's information.
+ *  - Handling the loading and state updates of the vehicle data.
+ *
+ * The ViewModel exposes a [StateFlow] called `vehicle` which emits the currently loaded [Vehicle] object,
+ * or `null` if no vehicle is loaded.
+ *
+ * Use the [loadVehicle] function to retrieve and load vehicle data by its ID.
+ *
+ * @property garageRepository The [GarageRepository] instance used to interact with the vehicle data source.
 */
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