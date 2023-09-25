@file:OptIn(ExperimentalMaterial3Api::class)

package com.moneyminions.presentation.screen.travellist

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.view.TravelCardView
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.FloatingButtonColor
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.travellist.TravelListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "TravelListScreen_D210"

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelListScreen(
    travelListViewModel: TravelListViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Log.d(TAG, "TravelListScreen: on")
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    
    travelListViewModel.refresh()
    
    // 여행 목록 GET 호출 부분
    val travelListState by travelListViewModel.networkTravelList.collectAsState()
//    LaunchedEffect(Unit) {
//        travelListViewModel.getTravelList()
//    }
    

    
    NetworkResultHandler(
        state = travelListState,
        errorAction = {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("서버 오류")
            }
        },
        successAction = {
            Log.d(TAG, "travelListResult : $it ")
//            travelList = it.toMutableList()
        }
    )
    
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = "방 생성",
                        color = DarkGray,
                        style = pretendardBold14
                    )
                },
                icon = { Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    tint = PinkDarkest,
                    contentDescription = "room add icon") },
                containerColor = FloatingButtonColor,
                onClick = {
                    navController.navigate(Screen.CreateTravel.route)
                })
        }
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp, 24.dp, 16.dp, 0.dp),
            content = {
                itemsIndexed(
                    items = travelListViewModel.travelList.value,
                    key = {_, item: TravelRoomDto ->
                        item.hashCode()
                    }
                ) { _ , item: TravelRoomDto ->
                    
                    TravelRoomItem(
                        modifier = Modifier,
                        travelRoomDto = item,
                        onRemove = travelListViewModel::removeItem
                    )

                }
            },
        )
    }
}

@Composable
fun getResourceId(resName: String, resType: Class<*>): Int {
    val resId = try {
        val idField = resType.getDeclaredField(resName)
        idField.getInt(idField)
    } catch (e: Exception) {
        e.printStackTrace()
        -1
    }
    return resId
}

@Preview(showBackground = true)
@Composable
fun TravelListScreenPreview() {
    TravelListScreen(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelRoomItem(
    modifier: Modifier,
    travelRoomDto: TravelRoomDto,
    onRemove: (TravelRoomDto) -> Unit
) {
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(travelRoomDto)
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart) { // 오른쪽 -> 왼쪽으로 스와이프시 삭제
                show = false
                true
            } else false
        }, positionalThreshold = { 150.dp.toPx() }
    )
    AnimatedVisibility(
        show,exit = fadeOut(spring())
    ) {
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier,
            background = {
                DismissBackground(dismissState)
            },
            dismissContent = {
                TravelCardView(
                    modifier = modifier,
                    travelRoomDto = travelRoomDto,
                    iconId = getResourceId("ic_travel_2", R.drawable::class.java),
                )
            }
        )
    }
    
    LaunchedEffect(show) {
        if (!show) {
            delay(800)
            onRemove(currentItem)
            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color = when (dismissState.dismissDirection) {
        DismissDirection.StartToEnd -> Color.Transparent
        DismissDirection.EndToStart -> PinkDarkest
        null -> Color.Transparent
    }
    val direction = dismissState.dismissDirection

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(12.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            if (direction == DismissDirection.EndToStart) Icon(
                // make sure add baseline_archive_24 resource to drawable folder
                Icons.Default.Delete,
                contentDescription = "delete"
            )
        }
    }
}
