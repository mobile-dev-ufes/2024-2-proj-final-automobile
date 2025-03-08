package com.ufes.automobile.ui.garage
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufes.automobile.domain.repository.GarageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GarageViewModel @Inject constructor(
    private val garageRepository: GarageRepository
) : ViewModel() {
    private val _vehicles = MutableStateFlow("")
    val vehicles: MutableStateFlow<String> = _vehicles

    init {
        viewModelScope.launch {
            _vehicles.value = garageRepository.getVehicles().toString()
        }
    }
}