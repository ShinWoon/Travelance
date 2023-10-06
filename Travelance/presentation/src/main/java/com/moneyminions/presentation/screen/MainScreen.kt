package com.moneyminions.presentation.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold10
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.BottomNavItem
import com.moneyminions.presentation.navigation.NavGraph
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.MainViewModel

private const val TAG = "MainScreen_D210"
//var startDestination: String = Screen.Home.route //나중에 viewModel로 빼야함
//var startDestination: String = Screen.Login.route //나중에 viewModel로 빼야함

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class,
    ExperimentalPermissionsApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    startDestination: String,
    navController: NavHostController = rememberAnimatedNavController(),
    mainViewModel: MainViewModel,
    context: Context,
    roomId: Int? = 0
) {
    //  --------------------------여행 목록 GET 호출 부분 --------------------------
    val travelListState by mainViewModel.networkTravelList.collectAsState()
    NetworkResultHandler(
        state = travelListState,
        errorAction = {},
        successAction = {
            Log.d(TAG, "실행시 => travelListResult : $it ")
            mainViewModel.checkTravelRoom(it.toMutableList())
        },
    )
    
    LaunchedEffect(Unit) {
        mainViewModel.getTravelList()
    }
    // ------------------------------------------------------------------------------
    
    val isShowDialogState by mainViewModel.isShowDialog.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val permissionList: List<String> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else{
        listOf()
    }
    val permissionState = rememberMultiplePermissionsState(permissions = permissionList)
    if (permissionState.allPermissionsGranted) { //모든 권한 허용된 상태
    } else if (permissionState.shouldShowRationale) {//한번 거절했을때
        Toast.makeText(context, "사용을 위해서 허가가 필요합니다!", Toast.LENGTH_LONG).show()
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
        mainViewModel.setIsShowDialog(true)
    } else { //최초
        Log.d("권한", "LoginDetailScreen: 최초")
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
        if(isShowDialogState){
            Column {
                AlertDialog(
                    onDismissRequest = { mainViewModel.setIsShowDialog(false) },
                    dialogTitle = "서비스 이용 알림",
                    dialogText = "해당 기능에 대한 권한 사용을 거부하였습니다. 기능 사용을 원하실 경우 휴대폰 설정 > 애플리케이션 관리자에서 해당 앱의 권한을 허용해주세요.",
                    icon = Icons.Default.Info
                )
            }
        }

    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            if (currentRoute != null && !Screen.checkToolBar(currentRoute!!)) {
                TopBar(
                    navController = navController,
                    currentRoute = currentRoute,
                )
            }
        },
        bottomBar = {
            if (currentRoute == null || isBottomNavItem(currentRoute!!)) {
                MainBottomNavigationBar(navController = navController)
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = White,
    ) {
        NavGraph(
            innerPaddings = it,
            startDestination = startDestination,
            navController = navController,
            mainViewModel = mainViewModel,
            snackbarHostState = snackbarHostState,
            resultRoomId = roomId
        )
    }

}


fun isBottomNavItem(route: String): Boolean {
    return when (route) {
        BottomNavItem.Home.route, BottomNavItem.TravelList.route, BottomNavItem.MyPage.route -> true
        else -> false
    }
}

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val bottomNavItems = listOf(
        BottomNavItem.TravelList,
        BottomNavItem.Home,
        BottomNavItem.MyPage,
    )
    NavigationBar(
        tonalElevation = 0.dp,
        modifier = Modifier.graphicsLayer {
            clip = true
            shadowElevation = 20f
        }
    ) {
        bottomNavItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            val current = backStackEntry.value?.destination?.route

//            Log.d(TAG, "MainBottomNavigationBar: $selected / $current")

            NavigationBarItem(
                selected = selected,
                // 해당 아이템의 route를 설정
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(current!!) {
                            inclusive = true
                        }
                    }
                },
                label = {
                    Text(
                        text = item.name,
                        style = pretendardSemiBold10,
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PinkDarkest,
                    selectedTextColor = PinkDarkest,
                    unselectedIconColor = TextGray,
                    unselectedTextColor = TextGray,
                    indicatorColor = White
                ),
            )
        }
    }
}

@Composable
fun AlertDialog(onDismissRequest: () -> Unit, dialogTitle: String, dialogText: String, icon: ImageVector) {
    Dialog(
        onDismissRequest = {onDismissRequest()}
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
        ){
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = dialogTitle
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = dialogText
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {onDismissRequest()}
                ) {
                    Text(text = "확인")
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
//    MainScreen(navController = rememberNavController())
}
