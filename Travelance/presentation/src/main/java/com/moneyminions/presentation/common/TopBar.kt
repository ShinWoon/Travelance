package com.moneyminions.presentation.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold20
import com.moneyminions.presentation.navigation.Screen.Companion.getToolBarIcon
import com.moneyminions.presentation.navigation.util.getScreenTitle
import com.moneyminions.presentation.theme.DarkerGray

private const val TAG = "싸피"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    currentRoute: String = "",
    topBarTitle: String = "",
    modifier: Modifier = Modifier
) {
    Log.d(TAG, "TopBar: ${currentRoute}")
    val titleIcon = getToolBarIcon(currentRoute)
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if(topBarTitle == "") getScreenTitle(currentRoute) else topBarTitle,
                    color = DarkerGray,
                    style = pretendardBold20,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (titleIcon != 0) {
                    Spacer(modifier = modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = titleIcon),
                        modifier = modifier.size(24.dp),
                        contentDescription = "topbar icon",
                    )
                }

            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_left),
                    contentDescription = "back button"
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        )
}

//@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
//@Composable
//fun TopBarPreview() {
//    TopBar(
//        navController = rememberNavController(),
//        title = "title",
//    )
//}
