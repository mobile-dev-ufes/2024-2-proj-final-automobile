package com.ufes.automobile.ui.recharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufes.automobile.data.local.entity.RechargeEntity
import com.ufes.automobile.domain.repository.RechargeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [RechargeViewModel] is a ViewModel class responsible for managing the UI-related data and logic
 * associated with recharging a vehicle. It interacts with the [RechargeRepository] to handle
 * data persistence.
 *
 * @property repository An instance of [RechargeRepository] used to access and manipulate
 *                    recharge data in the data source. Injected by Hilt.
 */
@HiltViewModel
class RechargeViewModel @Inject constructor(
    private val repository: RechargeRepository
) : ViewModel() {

    fun saveRecharge(
        vehicleId: Int,
        isElectric: Boolean,
        amount: Float,
        cost: Float,
        date: Long
    ) {
        viewModelScope.launch {
            val recharge = RechargeEntity(
                vehicleId = vehicleId,
                isElectric = isElectric,
                amount = amount,
                cost = cost,
                date = date
            )
            repository.addRecharge(recharge)
        }
    }
}