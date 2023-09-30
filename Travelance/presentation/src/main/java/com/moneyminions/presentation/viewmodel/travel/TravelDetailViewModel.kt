package com.moneyminions.presentation.viewmodel.travel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.domain.usecase.traveldetail.GetMyPaymentUseCase
import com.moneyminions.domain.usecase.traveldetail.GetTravelDetailInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "싸피"
@HiltViewModel
class TravelDetailViewModel @Inject constructor(
    private val getMyPaymentUseCase: GetMyPaymentUseCase,
    private val getTravelDetailInfoUseCase: GetTravelDetailInfoUseCase,
): ViewModel() {
    private val _myPaymentListState = MutableStateFlow<NetworkResult<List<TravelPaymentDto>>>(NetworkResult.Idle)
    val myPaymentListState = _myPaymentListState.asStateFlow()

    private val _travelDetailInfoState = MutableStateFlow<NetworkResult<TravelDetailInfoDto>>(NetworkResult.Idle)
    val travelDetailInfoState = _travelDetailInfoState.asStateFlow()

    fun getMyPaymentList() = viewModelScope.launch {
        _myPaymentListState.value = NetworkResult.Loading
        _myPaymentListState.emit(getMyPaymentUseCase.invoke())
        Log.d(TAG, "getMyPaymentList: ${myPaymentListState.value}")
    }

    fun getTravelDetailInfo() = viewModelScope.launch {
        _travelDetailInfoState.value = NetworkResult.Loading
        _travelDetailInfoState.emit(getTravelDetailInfoUseCase.invoke())
        Log.d(TAG, "getTravelDetailInfo: ${travelDetailInfoState.value}")
    }
}