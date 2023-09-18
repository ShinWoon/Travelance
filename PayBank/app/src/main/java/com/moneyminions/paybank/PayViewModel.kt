package com.moneyminions.paybank

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PayViewModel: ViewModel() {

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