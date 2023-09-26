package com.moneyminions.paybank.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.moneyminions.paybank.model.NetworkResult

@Composable
fun <T> NetworkResultHandler(
    state: NetworkResult<T>,
    errorAction: () -> Unit,
    successAction: (data: T) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    if(isLoading) {
    }

    LaunchedEffect(state) {
        when (state) {
            is NetworkResult.Error -> { errorAction() } // Handle the error here.
            NetworkResult.Idle -> { isLoading = false } // Handle the idle state here.
            NetworkResult.Loading -> { isLoading = true }
            is NetworkResult.Success -> {
                isLoading = false
                successAction(state.data)
            }
        }
    }
}