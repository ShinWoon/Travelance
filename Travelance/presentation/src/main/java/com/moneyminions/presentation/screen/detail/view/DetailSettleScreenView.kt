package com.moneyminions.presentation.screen.detail.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.Gray

private const val TAG = "싸피"
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSettleScreenView(
    modifier: Modifier = Modifier,
    publicMoneyList: List<TravelPaymentDto>,
    myPaymentList: List<TravelPaymentDto>,
    isDone: Boolean,
    changeValue: (TravelPaymentDto) -> Unit,
    deleteDialog: () -> Unit,
    selectedIdx: Int,
    myPaymentRowSelect: (MutableMap<String, Any>) -> Unit,
    myPaymentAccept: () -> Unit,
    getMyPayment: () -> Unit,
    setSettle: () -> Unit,
    resetIdx: () -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    // Modal Bottom Sheet이 닫힐 때 호출될 콜백
    if (showBottomSheet) {
        DetailMyPaymentDetailsView(
            myPaymentList = myPaymentList,
            onDismissSheet = {
                resetIdx()
                showBottomSheet = false
            },
            selectedIdx = selectedIdx,
            myPaymentRowSelect = myPaymentRowSelect,
            myPaymentAccept = myPaymentAccept,
            resetIdx = resetIdx,
            )
    }
    var helpDialog by remember { mutableStateOf(false) }
    if (helpDialog) {
        DetailHelpDialog(
            content = "공금 내역을 길게 눌러 삭제하세요\n\n나의 공금을 전부 입력된 것을 확인한 후 정산하기를 눌러 정산 요청하세요",
            onDismiss = {
                helpDialog = false
            }
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = modifier.weight(9f)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_help
                        ),
                        tint = DarkGray,
                        contentDescription = "help icon",
                        modifier = modifier
                            .size(24.dp)
                            .clickable {
                                helpDialog = true
                            })
                    Spacer(modifier = modifier.width(8.dp))
                    Text(
                        text = "도움",
                        color = DarkGray,
                        style = CustomTextStyle.pretendardSemiBold14
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = {
                            showBottomSheet = true
                            getMyPayment()
                        },
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        contentPadding = PaddingValues(
                            start = 0.dp,
                            top = 0.dp,
                            end = 0.dp,
                            bottom = 0.dp,
                        ),
                        modifier = modifier.wrapContentWidth(),
                    ) {
                        Text(
                            text = "공금 추가",
                            color = DarkerGray,
                            style = pretendardBold14,
                        )
                        Spacer(modifier = modifier.width(ButtonDefaults.IconSpacing))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_right),
                            tint = DarkerGray,
                            modifier = modifier.size(24.dp),
                            contentDescription = "add settle button",
                        )
                    }
                }
            }
            DetailPublicMoneySettleCardView(publicMoneyList = publicMoneyList, changeValue = changeValue, deleteDialog = deleteDialog)
        }
        MinionPrimaryButton(
            content = "정산요청",
            isEnabled = mutableStateOf(!isDone),
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            setSettle()
        }
    }
}
