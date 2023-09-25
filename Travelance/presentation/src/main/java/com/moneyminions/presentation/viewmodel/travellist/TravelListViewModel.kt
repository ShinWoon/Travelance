package com.moneyminions.presentation.viewmodel.travellist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.usecase.travellist.GetTravelListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelListViewModel @Inject constructor(
    private val getTravelListUseCase: GetTravelListUseCase
): ViewModel() {

    private val _travelList = MutableStateFlow<NetworkResult<List<TravelRoomDto>>>(NetworkResult.Idle)
    val travelList = _travelList.asStateFlow()
    fun getTravelList() {
        viewModelScope.launch {
            _travelList.emit(getTravelListUseCase.invoke())
        }
    }
}