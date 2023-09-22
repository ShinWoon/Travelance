package com.moneyminions.presentation.viewmodel.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.login.AuthenticationAccountInfoDto
import com.moneyminions.domain.model.login.AuthenticationAccountResultDto
import com.moneyminions.domain.model.login.LoginResultDto
import com.moneyminions.domain.usecase.login.PostAuthenticationAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountAuthenticationViewModel @Inject constructor(
    private val postAuthenticationAccountUseCase: PostAuthenticationAccountUseCase
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

    fun vaildCheck(accountInfoDto: AuthenticationAccountInfoDto): Boolean{
        return !(accountInfoDto.name == "" || accountInfoDto.accountNumber == "" || accountInfoDto.bankName == "")
    }

    private val _authenticationResult = MutableStateFlow<NetworkResult<AuthenticationAccountResultDto>>(NetworkResult.Idle)
    val authenticationResult = _authenticationResult.asStateFlow()
    fun postAuthenticationAccount(accountInfoDto: AuthenticationAccountInfoDto){
        viewModelScope.launch {
            _authenticationResult.emit(postAuthenticationAccountUseCase.invoke(accountInfoDto))
        }
    }
}