package com.ufes.automobile.ui.maintenance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufes.automobile.data.local.entity.MaintenanceReminderEntity
import com.ufes.automobile.domain.repository.MaintenanceReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaintenanceReminderViewModel @Inject constructor(
    private val repository: MaintenanceReminderRepository
) : ViewModel() {

    fun saveMaintenanceReminder(
        vehicleId: Int,
        description: String,
        reminderDate: Long
    ) {
        viewModelScope.launch {
            val maintenanceReminder = MaintenanceReminderEntity(
                vehicleId = vehicleId,
                description = description,
                reminderDate = reminderDate
            )
            repository.addMaintenanceReminder(maintenanceReminder)
        }
    }
}