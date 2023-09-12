package com.moneyminions.presentation.viewmodel.handwriting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class HandWritingViewModel @Inject constructor(

): ViewModel(){
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
}