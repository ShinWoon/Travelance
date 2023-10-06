package com.moneyminions.presentation.viewmodel.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.model.example.ExampleDto
import com.moneyminions.domain.usecase.mypage.GetMemberInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMemberInfoUseCase: GetMemberInfoUseCase
): ViewModel() {

    private val _memberInfoResult = MutableStateFlow<NetworkResult<MemberInfo>>(NetworkResult.Idle)
    val memberInfoResult = _memberInfoResult.asStateFlow()
    fun getMemberInfo(){
        viewModelScope.launch {
            _memberInfoResult.emit(getMemberInfoUseCase.invoke())
        }
    }


    private val _cardList = MutableStateFlow<List<CardDto>>(listOf())
    val cardList: StateFlow<List<CardDto>> = _cardList
    fun setCardList(list: List<CardDto>){
        viewModelScope.launch {
            _cardList.emit(list)
        }
    }

    private val _accountList = MutableStateFlow<List<AccountDto>>(listOf())
    val accountList: StateFlow<List<AccountDto>> = _accountList
    fun setAccountList(list: List<AccountDto>){
        viewModelScope.launch {
            _accountList.emit(list)
        }
    }

    private val _nickname = MutableStateFlow<String>("")
    val nickname: StateFlow<String> = _nickname
    fun setNickname(nickname: String){
        viewModelScope.launch {
            _nickname.emit(nickname)
        }
    }

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password
    fun setPassword(password: String){
        viewModelScope.launch {
            _password.emit(password)
        }
    }


}