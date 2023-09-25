package com.moneyminions.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.usecase.login.GetAccountListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountListViewModel @Inject constructor(
    private val getAccountListUseCase: GetAccountListUseCase
): ViewModel() {

    private val _accountListResult = MutableStateFlow<NetworkResult<List<AccountDto>>>(NetworkResult.Idle)
    val accountListResult = _accountListResult.asStateFlow()
    fun getAccountList(){
        viewModelScope.launch {
            _accountListResult.emit(getAccountListUseCase.invoke())
        }
    }

    private val _accountList = MutableStateFlow<List<AccountDto>>(mutableListOf())
    val accoutList: StateFlow<List<AccountDto>> = _accountList
    suspend fun setAccountList(list: List<AccountDto>){
        _accountList.emit(list)
    }

}