package com.moneyminions.presentation.viewmodel.travel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TravelDoneViewModel @Inject constructor(

) : ViewModel() {
    companion object {
        val categoryColorMap = mapOf<String, Color>(
            "식비" to ,
            "커피와 디저트" to ,
            "주류" to,
            "마트" to ,
            "편의점" to ,
            "교통/자동차" to,
            "숙소" to ,
            "쇼핑" to ,
            "레저" to ,
        )
    }
}
