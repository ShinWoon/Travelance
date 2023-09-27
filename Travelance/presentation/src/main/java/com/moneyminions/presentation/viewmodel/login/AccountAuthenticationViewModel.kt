package com.moneyminions.presentation.viewmodel.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.login.AuthenticationAccountInfoDto
import com.moneyminions.domain.model.login.AuthenticationAccountResultDto
import com.moneyminions.domain.model.login.LoginResultDto
import com.moneyminions.domain.usecase.login.ConfirmAuthenticationAccountUseCase
import com.moneyminions.domain.usecase.login.PostAuthenticationAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AccountAuthenticationVi D210"
@HiltViewModel
class AccountAuthenticationViewModel @Inject constructor(
    private val postAuthenticationAccountUseCase: PostAuthenticationAccountUseCase,
    private val confirmAuthenticationAccountUseCase: ConfirmAuthenticationAccountUseCase
): ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name
    fun setName(name: String){
        _name.value = name
    }

    private val _bankName = mutableStateOf("")
    val bankName: State<String> = _bankName
    fun setBankName(name: String){
        _bankName.value = name
    }

    private val _accountNumber = mutableStateOf("")
    val accountNumber: State<String> = _accountNumber
    fun setAccountNumber(number: String){
        _accountNumber.value = number
    }

    private val _verifyCode = mutableStateOf("")
    val verifyCode: State<String> = _verifyCode
    fun setVerifyCode(code: String){
        if (code.length > 4) {
            _verifyCode.value = code.substring(0,4)
        }else{
            _verifyCode.value = code
        }
    }

    fun validCheck(): Boolean{
        return !(name.value == "" || accountNumber.value == "" || bankName.value == "")
    }

    private val _authenticationResult = MutableStateFlow<NetworkResult<AuthenticationAccountResultDto>>(NetworkResult.Idle)
    val authenticationResult = _authenticationResult.asStateFlow()
    fun postAuthenticationAccount(){
        val authenticationAccountInfo = AuthenticationAccountInfoDto(
            name = name.value,
            bankName =  bankName.value,
            accountNumber = accountNumber.value
        )
        Log.d(TAG, "Account : $authenticationAccountInfo")
        viewModelScope.launch {
            _authenticationResult.emit(postAuthenticationAccountUseCase.invoke(authenticationAccountInfo))
        }
    }

    private val _confirmResult = MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val confirmResult = _confirmResult.asStateFlow()
    fun confirmAuthenticationAccount(){
        val authenticationAccountInfo = AuthenticationAccountInfoDto(
            name = name.value,
            bankName =  bankName.value,
            accountNumber = accountNumber.value,
            verifyCode = verifyCode.value
        )
        Log.d(TAG, "Account : $authenticationAccountInfo")
        viewModelScope.launch {
            _confirmResult.emit(confirmAuthenticationAccountUseCase.invoke(authenticationAccountInfo))
        }
    }

    suspend  fun refreshNetworkState(){
        _authenticationResult.emit(NetworkResult.Idle)
        _confirmResult.emit(NetworkResult.Idle)
    }

    private val _isShowDialog = MutableStateFlow<Boolean>(false)
    val isShowDialog: StateFlow<Boolean> = _isShowDialog
    fun setIsShowDialog(value: Boolean){
        viewModelScope.launch {
            _isShowDialog.emit(value)
        }
    }
}