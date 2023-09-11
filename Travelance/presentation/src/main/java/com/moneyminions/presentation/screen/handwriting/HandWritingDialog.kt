package com.moneyminions.presentation.screen.handwriting

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun HandWritingDialog(
    openHandWritingDialog: Boolean
) {

    if(openHandWritingDialog) {
        Dialog(onDismissRequest = {
//            openHandWritingDialog = false
        }) {

        }
    }
}