package com.ufes.automobile.ui.displacement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufes.automobile.data.local.entity.DisplacementEntity
import com.ufes.automobile.domain.repository.DisplacementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [DisplacementViewModel] is a [ViewModel] responsible for managing the UI-related data
 * and interactions for displacement operations. It interacts with the [DisplacementRepository]
 * to perform data persistence operations related to displacements.
 *
 * This ViewModel is annotated with `@HiltViewModel` to enable dependency injection with Hilt.
 * It receives an instance of [DisplacementRepository] through its constructor.
 *
 * @property repository The [DisplacementRepository] used for data access operations related to displacements.
 */
@HiltViewModel
class DisplacementViewModel @Inject constructor(
    private val repository: DisplacementRepository
) : ViewModel() {

    fun saveDisplacement(
        vehicleId: Int,
        distance: Float,
        date: Long,
        origin: String,
        destination: String
    ) {
        viewModelScope.launch {
            val displacement = DisplacementEntity(
                vehicleId = vehicleId,
                distance = distance,
                date = date,
                origin = origin,
                destination = destination
            )
            repository.addDisplacement(displacement)
        }
    }
}