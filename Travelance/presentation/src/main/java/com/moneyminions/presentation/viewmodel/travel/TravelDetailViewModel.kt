package com.moneyminions.presentation.viewmodel.travel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.PaymentCompleteDto
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentChangeInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.domain.usecase.traveldetail.GetMyPaymentUseCase
import com.moneyminions.domain.usecase.traveldetail.GetTravelDetailInfoUseCase
import com.moneyminions.domain.usecase.traveldetail.SetSettleStateUseCase
import com.moneyminions.domain.usecase.traveldetail.UpdateTravelPaymentInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D210"
@HiltViewModel
class TravelDetailViewModel @Inject constructor(
    private val getMyPaymentUseCase: GetMyPaymentUseCase,
    private val getTravelDetailInfoUseCase: GetTravelDetailInfoUseCase,
    private val updateTravelPaymentInfoUseCase: UpdateTravelPaymentInfoUseCase,
    private val setSettleStateUseCase: SetSettleStateUseCase,
): ViewModel() {
    private val _myPaymentListState = MutableStateFlow<NetworkResult<List<TravelPaymentDto>>>(NetworkResult.Idle)
    val myPaymentListState = _myPaymentListState.asStateFlow()

    private val _travelDetailInfoState = MutableStateFlow<NetworkResult<TravelDetailInfoDto>>(NetworkResult.Idle)
    val travelDetailInfoState = _travelDetailInfoState.asStateFlow()

    private val _updateTravelPaymentInfoState = MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val updateTravelPaymentInfoState = _updateTravelPaymentInfoState.asStateFlow()

    private val _setSettleStateState = MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val setSettleStateState = _setSettleStateState.asStateFlow()

    fun getMyPaymentList(roomId: Int) = viewModelScope.launch {
        _myPaymentListState.value = NetworkResult.Loading
        _myPaymentListState.emit(getMyPaymentUseCase.invoke(roomId = roomId))
        Log.d(TAG, "getMyPaymentList: ${myPaymentListState.value}")
    }

    fun getTravelDetailInfo(roomId: Int) = viewModelScope.launch {
        _travelDetailInfoState.value = NetworkResult.Loading
        _travelDetailInfoState.emit(getTravelDetailInfoUseCase.invoke(roomId = roomId))
        Log.d(TAG, "getTravelDetailInfo: ${travelDetailInfoState.value}")
    }

    fun updateTravelPaymentInfo(travelPaymentChangeInfo: TravelPaymentChangeInfoDto) = viewModelScope.launch {
        Log.d(TAG, "updateTravelPaymentInfo: $travelPaymentChangeInfo")
        _updateTravelPaymentInfoState.value = NetworkResult.Loading
        _updateTravelPaymentInfoState.emit(updateTravelPaymentInfoUseCase.invoke(travelPaymentChangeInfo))
    }

    fun setSettleState(paymentCompleteDto: PaymentCompleteDto) = viewModelScope.launch {
        _setSettleStateState.value = NetworkResult.Loading
        _setSettleStateState.emit(setSettleStateUseCase.invoke(paymentCompleteDto))
    }
}