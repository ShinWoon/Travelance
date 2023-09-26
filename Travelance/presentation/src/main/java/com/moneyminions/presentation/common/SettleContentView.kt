package com.moneyminions.presentation.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold12
import com.moneyminions.presentation.screen.detail.view.DetailCommonText
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.utils.MoneyUtils

private const val TAG = "싸피"

@Composable
fun SettleContentView(
    modifier: Modifier,
    payDate: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DetailCommonText(text = "사용내역")
            DetailCommonText(text = MoneyUtils.makeComma(189000))
        }
        Text(
            text = payDate,
            color = TextGray,
            style = pretendardSemiBold12,
        )
        Spacer(modifier = modifier.height(16.dp))
        Divider(
            color = DividerDefaults.color,
            thickness = (0.5).dp,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettleEditButton(
    icon: Int,
    iconColor: Color,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentEnforcement provides false,
    ) {
        IconButton(
            onClick = { Log.d(TAG, "SettleEditButton: clicked") },
            modifier = modifier.wrapContentSize()
                .background(color = Color.Transparent, shape = RectangleShape),
        ) {
            Icon(
                painter = painterResource(id = icon),
                tint = iconColor,
                modifier = modifier.size(20.dp),
                contentDescription = null,
            )
        }
    }
}
