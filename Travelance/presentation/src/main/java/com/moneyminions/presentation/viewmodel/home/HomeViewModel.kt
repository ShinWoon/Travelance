package com.moneyminions.presentation.viewmodel.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

private const val TAG = "HomeViewModel"
class HomeViewModel @Inject constructor(

): ViewModel(){
    private val _isTravelStart = mutableStateOf(false)
    val isTravelStart: State<Boolean> = _isTravelStart
    fun setTravelStart(check: Boolean) {
        _isTravelStart.value = check
    }


}