package com.ufes.automobile.ui.maintenance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufes.automobile.data.local.entity.MaintenanceReminderEntity
import com.ufes.automobile.domain.repository.MaintenanceReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [MaintenanceReminderViewModel] is a ViewModel class responsible for managing
 * maintenance reminder data and interactions with the [MaintenanceReminderRepository].
 *
 * It handles the business logic for creating and saving maintenance reminders.
 * It uses [Hilt] for dependency injection and [viewModelScope] for coroutine management.
 *
 * @property repository An instance of [MaintenanceReminderRepository] used for data access.
 */
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