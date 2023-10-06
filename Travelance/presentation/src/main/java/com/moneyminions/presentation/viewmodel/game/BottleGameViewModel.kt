package com.moneyminions.presentation.viewmodel.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class BottleGameViewModel @Inject constructor(

) : ViewModel() {
    private val _isLaunch = mutableStateOf(false)
    val isLaunch: State<Boolean> = _isLaunch
    fun setIsLaunch(value: Boolean) {
        _isLaunch.value = value
    }
    
}