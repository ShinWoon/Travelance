package com.moneyminions.presentation.viewmodel.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountAuthenticationViewModel @Inject constructor(

): ViewModel() {
    private val _authenticationCode = mutableStateOf("")
    val authenticationCode: State<String> = _authenticationCode
    fun setAuthenticationCode(name: String){
        if (name.length > 4) {
            _authenticationCode.value = name.substring(0,4)
        }else{
            _authenticationCode.value = name
        }
    }
}