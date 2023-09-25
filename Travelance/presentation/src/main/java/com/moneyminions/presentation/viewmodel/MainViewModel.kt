package com.moneyminions.presentation.viewmodel

import android.accounts.Account
import androidx.lifecycle.ViewModel
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): ViewModel() {

    val memberInfo: MemberInfo = MemberInfo(listOf(), listOf(), "", "")
    fun setMemberAccountList(list: List<AccountDto>){
        memberInfo.accountList = list
    }
    fun setMemberCardList(list: List<CardDto>){
        memberInfo.cardList = list
    }
    fun setNickname(nickname: String){
        memberInfo.nickname = nickname
    }
    fun setPassword(password: String){
        memberInfo.password = password
    }
}