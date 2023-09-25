package com.moneyminions.presentation.viewmodel.travellist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.usecase.travellist.CreateTravelRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTravelViewModel @Inject constructor(
    private val createTravelRoomUseCase: CreateTravelRoomUseCase,
) : ViewModel() {
    
    private val _travelName = mutableStateOf("")
    val travelName: State<String> = _travelName
    fun setTravelName(name: String) {
        _travelName.value = name
    }
    
    private val _travelBudget = mutableStateOf("")
    val travelBudget: State<String> = _travelBudget
    fun setTravelBudget(budget: String) {
        _travelBudget.value = budget
    }
    
    private val _startDate = mutableStateOf("")
    val startDate: State<String> = _startDate
    fun setStartDate(date: String) {
        _startDate.value = date
    }
    
    private val _endDate = mutableStateOf("")
    val endDate: State<String> = _endDate
    fun setEndDate(date: String) {
        _endDate.value = date
    }
    
    fun InputCheck(): Boolean {
        return _travelName.value.isNullOrEmpty() || _travelBudget.value.isNullOrEmpty() || _startDate.value.isNullOrEmpty() || _endDate.value.isNullOrEmpty()
    }

    /**
     * 여행방 생성
     */
    private val _createTravelRoomResult = MutableStateFlow<NetworkResult<String>>(NetworkResult.Idle)
    val createTravelRoomResult = _createTravelRoomResult.asStateFlow()
    fun createTravelRoom() {
        viewModelScope.launch {
            _createTravelRoomResult.emit(createTravelRoomUseCase.invoke(
                TravelRoomDto(
                    budget = _travelBudget.value.toInt(),
                    endDate = _endDate.value,
                    startDate = _startDate.value,
                    travelName = _travelName.value
                )
            ))
        }
    }
}