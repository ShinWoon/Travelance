package com.moneyminions.paybank.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moneyminions.paybank.model.FcmTokenRequest
import com.moneyminions.paybank.model.NetworkResult
import com.moneyminions.paybank.model.NormalResponse
import com.moneyminions.paybank.model.PaymentRequest
import com.moneyminions.paybank.model.PaymentResponse
import com.moneyminions.paybank.service.BankService
import com.moneyminions.paybank.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "PayViewModel D210"
@HiltViewModel
class PayViewModel @Inject constructor(
    private val bankService: BankService
): ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name
    fun setName(name: String){
        _name.value = name
    }
    private val _postFcmTokenResult = MutableStateFlow<NetworkResult<NormalResponse>>(NetworkResult.Idle)
    val postFcmTokenResult = _postFcmTokenResult.asStateFlow()
    fun postFcmToken(){
        Log.d(TAG, "postFcmToken: $name , ${Constants.fcmToken}")
        viewModelScope.launch {
            bankService.postFcmToken(FcmTokenRequest(name = _name.value, fcmToken = Constants.fcmToken))
        }
    }

    private val _postPaymentResult = MutableStateFlow<NetworkResult<PaymentResponse>>(NetworkResult.Idle)
    val postPaymentResult = _postPaymentResult.asStateFlow()
    fun postPaymentRequest(){
        viewModelScope.launch {
            val postBody = PaymentRequest(
                cardNumber = cardNumber.value,
                cvc = cvc.value,
                paymentAmount = amount.value.toInt(),
                paymentContent = storeName.value,
                storeAddress = storeAddress.value,
                storeSector = storeType.value
            )
            Log.d(TAG, "postPaymentRequest body $postBody")
            try {
                _postPaymentResult.emit(
                    bankService.postPaymentRequest(
                        PaymentRequest(
                            cardNumber = cardNumber.value,
                            cvc = cvc.value,
                            paymentAmount = amount.value.toInt(),
                            paymentContent = storeName.value,
                            storeAddress = storeAddress.value,
                            storeSector = storeType.value
                        )
                    )
                )
            }catch (e: Exception){
                Log.d(TAG, "postPaymentRequest: $e")
            }
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

    private val _isShowDialog = MutableStateFlow(false)
    val isShowDialog = _isShowDialog.asStateFlow()
    fun setIsShowDialog(value: Boolean){
        viewModelScope.launch {
            _isShowDialog.emit(value)
        }
    }
}