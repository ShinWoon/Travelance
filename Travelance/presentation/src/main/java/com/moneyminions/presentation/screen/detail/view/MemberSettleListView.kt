package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MemberSettleListView() {
    val modifier: Modifier = Modifier
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 16.dp),
        ) {
            item {
                Spacer(modifier = modifier.height(16.dp))
                DetailCommonTitleText(text = "멤버별 사용량")
                Spacer(modifier = modifier.height(8.dp))
            }
            items(10) {
                MemberSettleView(modifier = modifier)
            }
        }
    }
}
