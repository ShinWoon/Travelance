package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold16
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold14
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.FloatingButtonColor
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMyPaymentDetailsView(
    onDismissSheet: () -> Unit, // Optional callback for dismissing the sheet
    modifier: Modifier = Modifier,
    myPaymentList: List<TravelPaymentDto>,
    selectedIdx: Int,
    myPaymentRowSelect: (MutableMap<String, Any>) -> Unit,
    myPaymentAccept: () -> Unit,
    resetIdx: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var bottomSheetHelpDialog by remember { mutableStateOf(false) }
    if (bottomSheetHelpDialog) {
        DetailHelpDialog(
            content = "결제 내역을 눌러 공금에 추가하세요",
            onDismiss = {
                bottomSheetHelpDialog = false
                resetIdx()
            }
        )
    }
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
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_help),
                        tint = DarkGray,
                        contentDescription = "help icon",
                        modifier = modifier
                            .size(24.dp)
                            .clickable {
                                bottomSheetHelpDialog = true
                            },
                    )
                    Spacer(modifier = modifier.width(8.dp))
                    Text(
                        text = "도움",
                        color = DarkGray,
                        style = pretendardSemiBold14
                    )
                }
                TextButton(
                    onClick = {
                        myPaymentAccept()
                        resetIdx()
                        onDismissSheet()
                    },
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        text = "추가하기",
                        color = PinkDarkest,
                        style = pretendardBold16,
                    )
                }
            }
            MyPaymentDetails(
                myPaymentList = myPaymentList,
                selectedIdx = selectedIdx,
                myPaymentRowSelect = myPaymentRowSelect,
            )
        }
    }
}

@Composable
fun MyPaymentDetails(
    modifier: Modifier = Modifier,
    myPaymentList: List<TravelPaymentDto>,
    selectedIdx: Int,
    myPaymentRowSelect: (MutableMap<String, Any>) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        itemsIndexed(myPaymentList) { index, item ->
            val backgroundColor = if (index == selectedIdx) PinkLight.copy(alpha = 0.5f) else Color.Transparent
            DetailMyPaymentSettleContentView(
                backgroundColor = backgroundColor,
                paymentInfo = item,
                myPaymentRowSelect = myPaymentRowSelect,
                idx = index,
            )
        }
    }
}
