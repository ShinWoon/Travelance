package com.moneyminions.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.LottieLoader

@Composable
fun <T> NetworkResultHandler(
    state: NetworkResult<T>,
    errorAction: () -> Unit,
    successAction: (data: T) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    if(isLoading) {
        LoadingDiloag(onDismissRequest = {isLoading = false}, dialogTitle = "잠시만 기다려주세요")
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

@Composable
fun LoadingDiloag(onDismissRequest: () -> Unit, dialogTitle: String){
    Dialog(
        onDismissRequest = {onDismissRequest()}
    ) {
        Surface(
            color = Color.Transparent,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.size(150.dp)
        ){
            Image(painter = painterResource(id = R.drawable.travelance_logo), contentDescription = "loadingLogo")
        }
    }
}