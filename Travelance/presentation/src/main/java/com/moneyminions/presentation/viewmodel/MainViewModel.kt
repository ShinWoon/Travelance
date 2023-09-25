package com.moneyminions.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.moneyminions.domain.model.MemberInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): ViewModel() {

    val memberInfo: MemberInfo = MemberInfo(listOf())
}