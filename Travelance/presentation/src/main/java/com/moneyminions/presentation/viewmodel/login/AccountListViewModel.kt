package com.moneyminions.presentation.viewmodel.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.usecase.login.GetAccountListUseCase
import com.moneyminions.presentation.viewmodel.MainViewModel
import com.moneyminions.presentation.viewmodel.mypage.EditUserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountListViewModel @Inject constructor(
    private val getAccountListUseCase: GetAccountListUseCase,
): ViewModel() {

    private val _accountListResult = MutableStateFlow<NetworkResult<List<AccountDto>>>(NetworkResult.Idle)
    val accountListResult = _accountListResult.asStateFlow()
    fun getAccountList(){
        viewModelScope.launch {
            _accountListResult.emit(getAccountListUseCase.invoke())
        }
    }

    private var _existingAccountList = MutableStateFlow<List<AccountDto>>(listOf())
    var existingAccountList: StateFlow<List<AccountDto>> = _existingAccountList
    fun setExistingAccountList(editUserViewModel: EditUserViewModel){
        viewModelScope.launch {
            _existingAccountList.emit(editUserViewModel.accountList.value)
        }
    }


    fun isEmptyExistingAccountList(): Boolean{
        return if(_existingAccountList.value.isNotEmpty()) false
        else true
    }

    private val _accountList = MutableStateFlow<List<AccountDto>>(mutableListOf())
    val accoutList: StateFlow<List<AccountDto>> = _accountList
    suspend fun setAccountList(list: List<AccountDto>){
        _accountList.emit(
            list.map { account ->
                val isSelected = _existingAccountList.value.any { it.bankName == account.bankName && it.accountNumber == account.accountNumber }
                account.copy(isSelected = isSelected)
            }
        )
    }


}