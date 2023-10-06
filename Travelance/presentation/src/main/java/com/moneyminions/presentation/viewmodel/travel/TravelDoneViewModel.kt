package com.moneyminions.presentation.viewmodel.travel

import androidx.lifecycle.ViewModel
import com.moneyminions.presentation.theme.CategoryAccommodation
import com.moneyminions.presentation.theme.CategoryAlcohol
import com.moneyminions.presentation.theme.CategoryCoffee
import com.moneyminions.presentation.theme.CategoryDining
import com.moneyminions.presentation.theme.CategoryGroceries
import com.moneyminions.presentation.theme.CategoryLeisure
import com.moneyminions.presentation.theme.CategoryMinimarts
import com.moneyminions.presentation.theme.CategoryShopping
import com.moneyminions.presentation.theme.CategoryTransportation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TravelDoneViewModel @Inject constructor() : ViewModel() {
    companion object {
        val categoryColorMap = mapOf(
            "식비" to CategoryDining,
            "커피와 디저트" to CategoryCoffee,
            "주류" to CategoryAlcohol,
            "마트" to CategoryGroceries,
            "편의점" to CategoryMinimarts,
            "교통/자동차" to CategoryTransportation,
            "숙소" to CategoryAccommodation,
            "쇼핑" to CategoryShopping,
            "레저" to CategoryLeisure,
        )
    }
}
