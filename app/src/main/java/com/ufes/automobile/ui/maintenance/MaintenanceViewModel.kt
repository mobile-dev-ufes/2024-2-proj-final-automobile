package com.ufes.automobile.ui.maintenance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufes.automobile.data.local.entity.MaintenanceEntity
import com.ufes.automobile.domain.repository.MaintenanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [MaintenanceViewModel] is a [ViewModel] responsible for handling maintenance-related operations.
 * It interacts with the [MaintenanceRepository] to perform actions like saving maintenance records.
 *
 * This ViewModel is annotated with [HiltViewModel] for dependency injection using Hilt.
 * It uses [viewModelScope] to launch coroutines within the ViewModel's lifecycle.
 *
 * @property repository The [MaintenanceRepository] used to interact with the data layer for maintenance operations.
 */
@HiltViewModel
class MaintenanceViewModel @Inject constructor(
    private val repository: MaintenanceRepository
) : ViewModel() {

    fun saveMaintenance(
        vehicleId: Int,
        description: String,
        cost: Float,
        date: Long
    ) {
        viewModelScope.launch {
            val maintenance = MaintenanceEntity(
                vehicleId = vehicleId,
                description = description,
                cost = cost,
                date = date
            )
            repository.addMaintenance(maintenance)
        }
    }
}