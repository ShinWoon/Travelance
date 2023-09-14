package com.moneyminions.presentation.screen.home.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun DetailTabView(modifier: Modifier) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("")
}
