package com.ufes.automobile.ui.insurance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufes.automobile.data.local.entity.InsuranceEntity
import com.ufes.automobile.domain.repository.InsuranceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsuranceViewModel @Inject constructor(
    private val repository: InsuranceRepository
) : ViewModel() {

    fun saveInsurance(
        vehicleId: Int,
        insurer: String,
        policyNumber: String,
        assistanceDetails: String,
        startDate: Long,
        endDate: Long,
        cost: Float
    ) {
        viewModelScope.launch {
            val insurance = InsuranceEntity(
                vehicleId = vehicleId,
                insurer = insurer,
                policyNumber = policyNumber,
                assistanceDetails = assistanceDetails,
                startDate = startDate,
                endDate = endDate,
                cost = cost
            )
            repository.addInsurance(insurance)
        }
    }
}