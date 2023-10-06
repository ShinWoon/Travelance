package com.moneyminions.presentation.viewmodel.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.usecase.login.JoinOutUseCase
import com.moneyminions.domain.usecase.login.LogoutUseCase
import com.moneyminions.domain.usecase.preference.PutFCMTokenUseCase
import com.moneyminions.domain.usecase.preference.PutJwtTokenUseCase
import com.moneyminions.domain.usecase.preference.PutTravelingRoomIdUseCase
import com.moneyminions.domain.usecase.preference.RefreshPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val joinOutUseCase: JoinOutUseCase,
    private val refreshPreferenceUseCase: RefreshPreferenceUseCase
): ViewModel(){

    private val _logoutResult = MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val logoutResult = _logoutResult.asStateFlow()
    fun logout(){
        viewModelScope.launch {
            _logoutResult.emit(logoutUseCase.invoke())
            refreshPreferenceUseCase.invoke()
        }
    }

    private val _joinOutResult = MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val joinOutResult = _joinOutResult.asStateFlow()
    fun joinOut(){
        viewModelScope.launch {
            _joinOutResult.emit(joinOutUseCase.invoke())
            refreshPreferenceUseCase.invoke()
        }
    }
}