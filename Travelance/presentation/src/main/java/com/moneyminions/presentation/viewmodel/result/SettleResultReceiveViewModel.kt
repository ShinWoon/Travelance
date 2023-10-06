package com.moneyminions.presentation.viewmodel.result

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.FinalPaymentDto
import com.moneyminions.domain.model.traveldetail.SettleResultDto
import com.moneyminions.domain.usecase.traveldetail.GetSettleResultUseCase
import com.moneyminions.domain.usecase.traveldetail.PostFinalPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SettleResultReceiveView D210"
@HiltViewModel
class SettleResultReceiveViewModel @Inject constructor(
    private val getSettleResultUseCase: GetSettleResultUseCase,
    private val postFinalPaymentUseCase: PostFinalPaymentUseCase
): ViewModel(){

    private val _settleResult = MutableStateFlow<NetworkResult<SettleResultDto>>(
        NetworkResult.Idle)
    val settleResult = _settleResult.asStateFlow()

    fun getSettleResult(roomId: Int){
        viewModelScope.launch {
            _settleResult.emit(getSettleResultUseCase.invoke(roomId))
        }
    }

    private val _settleResultDto = mutableStateOf<SettleResultDto?>(null)
    val settleResultDto: State<SettleResultDto?> = _settleResultDto
    fun setSettleResultDto(data: SettleResultDto){
        _settleResultDto.value = data
    }

    private val _finalPaymentDto = mutableStateOf<FinalPaymentDto?>(null)
    val finalPaymentDto: State<FinalPaymentDto?> = _finalPaymentDto
    fun setFinalPaymentDto(roomId: Int){
        _finalPaymentDto.value = FinalPaymentDto(password = _password.value, roomNumber = roomId)
    }

    private val _finalPaymentResult = MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val finalPaymentResult = _finalPaymentResult.asStateFlow()
    fun postFinalPayment(){
        viewModelScope.launch {
            Log.d(TAG, "postFinalPayment에서 dto : ${_finalPaymentDto.value} ")
            _finalPaymentResult.emit(postFinalPaymentUseCase.invoke(_finalPaymentDto.value!!))
        }
    }

    private val _isShowDialog = MutableStateFlow<Boolean>(false)
    val isShowDialog: StateFlow<Boolean> = _isShowDialog
    fun setIsShowDialog(value: Boolean){
        viewModelScope.launch {
            _isShowDialog.emit(value)
        }
    }
    private val _password = mutableStateOf("")
    val password: State<String> = _password
    fun setPassword(password: String){
        if (password.length > 4) {
            _password.value = password.substring(0,4)
        }else{
            _password.value = password
        }
    }
}