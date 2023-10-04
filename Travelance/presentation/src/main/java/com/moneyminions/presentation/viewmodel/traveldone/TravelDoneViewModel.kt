package com.moneyminions.presentation.viewmodel.traveldone

import androidx.lifecycle.ViewModel
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldone.TravelDoneInfoTotalDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TravelDoneViewModel @Inject constructor(

): ViewModel() {
    private val _travelDoneInfoGetState = MutableStateFlow<NetworkResult<TravelDoneInfoTotalDto>>(NetworkResult.Loading)
    val travelDoneInfoGetState = _travelDoneInfoGetState.asStateFlow()
}