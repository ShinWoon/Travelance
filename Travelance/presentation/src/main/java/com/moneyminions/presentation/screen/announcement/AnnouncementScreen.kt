@file:OptIn(ExperimentalMaterial3Api::class)

package com.moneyminions.presentation.screen.announcement

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.theme.White


@Composable
fun AnnouncementScreen(
    navController: NavHostController,
) {
    
    var openAnnouncementWritingDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController = navController, title = "필독")
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openAnnouncementWritingDialog = true
                },
                Modifier.padding(4.dp),
                containerColor = White
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_document_add),
                    contentDescription = "add document"
                )
            }
        }
    
    
    ) {
        Surface(
            modifier = Modifier.padding(it),
        ) {
        
        }
        
        if (openAnnouncementWritingDialog) {
            AnnouncementWritingDialog(onDismiss = {openAnnouncementWritingDialog = false})
        }
    }
    
}