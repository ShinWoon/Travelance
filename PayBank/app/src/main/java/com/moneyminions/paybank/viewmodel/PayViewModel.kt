package com.moneyminions.paybank.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moneyminions.paybank.model.FcmTokenRequest
import com.moneyminions.paybank.service.BankService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PayViewModel D210"
@HiltViewModel
class PayViewModel @Inject constructor(
    private val bankService: BankService
): ViewModel() {

    fun postFcmToken(fcmTokenRequest: FcmTokenRequest){
        Log.d(TAG, "postFcmToken: ${fcmTokenRequest.fcmToken}")
        viewModelScope.launch {
            bankService.postFcmToken(fcmTokenRequest)
        }
    }

    private val _cardNumber = mutableStateOf("")
    val cardNumber: State<String> = _cardNumber
    fun setCardNumber(number: String){
        _cardNumber.value = number
    }

    private val _cvc = mutableStateOf("")
    val cvc: State<String> = _cvc
    fun setCvc(cvc: String){
        _cvc.value = cvc
    }

    private val _amount = mutableStateOf("")
    val amount: State<String> = _amount
    fun setAmount(amount: String){
        _amount.value = amount
    }

    private val _storeName = mutableStateOf("")
    val storeName: State<String> = _storeName
    fun setStoreName(name: String){
        _storeName.value = name
    }

    private val _storeType = mutableStateOf("")
    val storeType: State<String> = _storeType
    fun setStoreType(type: String){
        _storeType.value = type
    }

    private val _storeAddress = mutableStateOf("")
    val storeAddress: State<String> = _storeAddress
    fun setStoreAddress(address: String){
        _storeAddress.value = address
    }
}