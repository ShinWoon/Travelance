package com.moneyminions.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moneyminions.presentation.common.text.CustomTextStyle.bigTitleTextStyle

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val scrollableState = rememberScrollState()
    
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollableState),
    ) {
        Home()
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home()
}

@Composable
fun Home() {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        TopComponent()
        Spacer(modifier = Modifier.height(8.dp))
        MainComponent()
    }
}

@Composable
fun TopComponent() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.LightGray),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "여행 이름",
                style = bigTitleTextStyle,
            )
            
            IconButton(
                onClick = { },
            ) {
                Icon(
                    Icons.Outlined.KeyboardArrowRight,
                    contentDescription = "move detail",
                )
            }
        }
    }
}

@Composable
fun MainComponent() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.LightGray),
    ) {
    }
}

@Composable
fun MainComponentTop() {
}
