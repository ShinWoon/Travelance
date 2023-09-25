package com.moneyminions.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.usecase.login.GetCardListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    private val getCardListUseCase: GetCardListUseCase
): ViewModel() {

    private val _cardListResult = MutableStateFlow<NetworkResult<List<CardDto>>>(NetworkResult.Idle)
    val cardListResult = _cardListResult.asStateFlow()
    fun getCardList(){
        viewModelScope.launch {
            _cardListResult.emit(getCardListUseCase.invoke())
        }
    }

    private val _cardList = MutableStateFlow<List<CardDto>>(mutableListOf())
    val cardList: StateFlow<List<CardDto>> = _cardList
    suspend fun setCardList(list: List<CardDto>){
        _cardList.emit(list)
    }
}