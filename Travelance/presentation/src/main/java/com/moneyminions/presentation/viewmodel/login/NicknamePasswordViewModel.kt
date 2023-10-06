package com.moneyminions.presentation.viewmodel.login

import android.media.audiofx.DynamicsProcessing.Stage
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NicknamePasswordViewModel @Inject constructor(

): ViewModel() {

    private val _nickname = mutableStateOf("")
    val nickname: State<String> = _nickname
    fun setNickname(nickname: String){
        _nickname.value = nickname
    }

    private val _password = mutableStateOf("")
    val password: State<String> = _password
    fun setPassword(password: String){
        _password.value = password.take(4)
    }

    private val _confirmPassword = mutableStateOf("")
    val confirmPassword: State<String> = _confirmPassword
    fun setConfirmPassword(password: String){
        _confirmPassword.value = password.take(4)
    }
    fun checkPassword(): Boolean{
        return _password.value==_confirmPassword.value
    }
}