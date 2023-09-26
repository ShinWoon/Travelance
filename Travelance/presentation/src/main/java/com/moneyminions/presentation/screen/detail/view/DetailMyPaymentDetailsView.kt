package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle.pretendardRegular12
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.FloatingButtonColor
import com.moneyminions.presentation.utils.MoneyUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMyPaymentDetailsView(
    onDismissSheet: () -> Unit, // Optional callback for dismissing the sheet
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismissSheet() },
        containerColor = FloatingButtonColor,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            MyPaymentDetails()
            Spacer(modifier = modifier.height(8.dp))
            MinionPrimaryButton(content = "확인") {
            }
        }
    }
}

@Composable
fun MyPaymentDetails(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(10) {
            PaymentDetailContents(paymentTitle = "test", amount = 11111111)
        }
    }
}

@Composable
fun PaymentDetailContents(
    modifier: Modifier = Modifier,
    paymentTitle: String,
    amount: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = paymentTitle,
            color = DarkerGray,
            style = pretendardRegular12,
        )
        Text(
            text = MoneyUtils.makeComma(amount),
            color = DarkerGray,
            style = pretendardRegular12,
        )
    }
}
