package com.moneyminions.presentation.viewmodel.travellist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateTravelViewModel @Inject constructor(

): ViewModel() {

    private val _travelName = mutableStateOf("")
    val travelName: State<String> = _travelName
    fun setTravelName(name: String){
        _travelName.value = name
    }

    private val _travelBudget = mutableStateOf("")
    val travelBudget: State<String> = _travelBudget
    fun setTravelBudget(budget: String){
        _travelBudget.value = budget
    }

    private val _startDate = mutableStateOf("")
    val startDate: State<String> = _startDate
    fun setStartDate(date: String){
        _startDate.value = date
    }

    private val _endDate = mutableStateOf("")
    val endDate: State<String> = _endDate
    fun setEndDate(date: String){
        _endDate.value = date
    }

}