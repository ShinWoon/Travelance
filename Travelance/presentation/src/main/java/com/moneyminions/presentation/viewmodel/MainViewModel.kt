package com.moneyminions.presentation.viewmodel

import android.accounts.Account
import androidx.lifecycle.ViewModel
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.usecase.preference.GetJwtTokenUseCase
import com.moneyminions.domain.usecase.preference.GetRoleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRoleUseCase: GetRoleUseCase,
    private val getJwtTokenUseCase: GetJwtTokenUseCase
): ViewModel() {

//    fun getRole(): String{
//        return getRoleUseCase.invoke()
//    }
    fun getJwtToken(): JwtTokenDto {
        return getJwtTokenUseCase.invoke()
    }

}