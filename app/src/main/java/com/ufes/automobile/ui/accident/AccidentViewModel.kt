package com.ufes.automobile.ui.accident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufes.automobile.data.local.entity.AccidentEntity
import com.ufes.automobile.domain.repository.AccidentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [AccidentViewModel] is a ViewModel class responsible for managing accident-related data and operations.
 *
 * It interacts with the [AccidentRepository] to perform database operations related to accidents.
 *
 * @property repository An instance of [AccidentRepository] used to access and manipulate accident data.
 */
@HiltViewModel
class AccidentViewModel @Inject constructor(
    private val repository: AccidentRepository
) : ViewModel() {

    fun saveAccident(
        vehicleId: Int,
        date: Long,
        description: String,
        location: String
    ) {
        viewModelScope.launch {
            val accident = AccidentEntity(
                vehicleId = vehicleId,
                date = date,
                description = description,
                location = location
            )
            repository.addAccident(accident)
        }
    }

//    private val _accidentState = MutableStateFlow<AccidentState?>(null)
//    val accidentState: StateFlow<AccidentState?> = _accidentState
//
//    fun saveAccident(vehicleId: String, date: String, description: String) {
//        viewModelScope.launch {
//            _accidentState.value = AccidentState(isLoading = true)
//            try {
//                // Aqui você deve implementar a lógica para salvar o acidente
//                // Exemplo: salvar no Firebase Firestore
//                // val accident = mapOf("vehicleId" to vehicleId, "date" to date, "description" to description)
//                // Firebase.firestore.collection("accidents").add(accident)
//                _accidentState.value = AccidentState(isSuccess = true)
//            } catch (e: Exception) {
//                _accidentState.value = AccidentState(errorMessage = "Failed to save accident: ${e.message}")
//            }
//        }
//    }
}