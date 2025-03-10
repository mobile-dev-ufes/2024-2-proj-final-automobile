package com.ufes.automobile.ui.registry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufes.automobile.domain.model.Vehicle
import com.ufes.automobile.domain.repository.GarageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [RegistryViewModel] is a [ViewModel] responsible for handling the logic related to registering a new vehicle.
 * It interacts with the [GarageRepository] to save the vehicle's information.
 *
 * @property garageRepository The repository used to interact with the underlying data source for vehicles.
 */
@HiltViewModel
class RegistryViewModel @Inject constructor(
    private val garageRepository: GarageRepository
) : ViewModel() {
    fun saveVehicle(
        brand: String,
        model: String,
        manufacturingYear: Int,
        purchaseDate: Long,
        isElectric: Boolean,
        batteryCapacity: Float?,
        autonomy: Float?,
        tankCapacity: Float?
    ) {
        viewModelScope.launch {
            val vehicle = Vehicle(
                id = 0,
                brand = brand,
                model = model,
                manufacturingYear = manufacturingYear,
                purchaseDate = purchaseDate,
                isElectric = isElectric,
                batteryCapacity = if (isElectric) batteryCapacity else null,
                autonomy = if (isElectric) autonomy else null,
                tankCapacity = if (!isElectric) tankCapacity else null
            )
            garageRepository.addVehicle(vehicle)
        }
    }
}