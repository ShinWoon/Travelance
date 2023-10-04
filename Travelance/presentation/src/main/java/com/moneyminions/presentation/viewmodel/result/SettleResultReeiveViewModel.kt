package com.moneyminions.presentation.viewmodel.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.SettleResultDto
import com.moneyminions.domain.usecase.traveldetail.GetSettleResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettleResultReeiveViewModel @Inject constructor(
    private val getSettleResultUseCase: GetSettleResultUseCase
): ViewModel(){

    private val _settleResult = MutableStateFlow<NetworkResult<SettleResultDto>>(
        NetworkResult.Idle)
    val settleResult = _settleResult.asStateFlow()

    fun getSettleResult(roomId: Int){
        viewModelScope.launch {
            _settleResult.emit(getSettleResultUseCase.invoke(roomId))
        }
    }
}