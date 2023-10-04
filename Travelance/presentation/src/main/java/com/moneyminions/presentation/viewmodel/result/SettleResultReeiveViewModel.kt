package com.moneyminions.presentation.viewmodel.result

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.SettleResultDto
import com.moneyminions.domain.usecase.traveldetail.GetSettleResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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