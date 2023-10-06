package com.moneyminions.presentation.viewmodel.handwriting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.home.CashDto
import com.moneyminions.domain.usecase.home.CashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HandWritingViewModel_D210"

@HiltViewModel
class HandWritingViewModel @Inject constructor(
    private val cashUseCase: CashUseCase,
) : ViewModel() {
    private val _useMoneyContent = mutableStateOf("")
    val useMoneyContent: State<String> = _useMoneyContent
    fun setUseMoneyContent(content: String) {
        _useMoneyContent.value = content
    }
    
    private val _useMoney = mutableStateOf("")
    val useMoney: State<String> = _useMoney
    fun setUseMoney(money: String) {
        _useMoney.value = money
    }
    
    fun checkInput(): Boolean {
        return _useMoney.value.isNotEmpty() && _useMoneyContent.value.isNotEmpty()
    }
    
    private val _cashResult = MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val cashResult = _cashResult.asStateFlow()
    fun requestCash(roomId: Int) {
        viewModelScope.launch {
            _cashResult.emit(
                cashUseCase.invoke(
                    cashDto = CashDto(
                        roomNumber = roomId,
                        paymentContent = _useMoneyContent.value,
                        paymentAmount = _useMoney.value.toInt(),
                    )
                )
            )
        }
    }

    fun initCashResult(){
        viewModelScope.launch {
            _cashResult.emit(NetworkResult.Idle)
        }
    }
    
    
}