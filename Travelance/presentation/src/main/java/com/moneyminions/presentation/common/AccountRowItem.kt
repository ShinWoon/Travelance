package com.moneyminions.presentation.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.PinkDarkest

private const val TAG = "AccountRowItem D210"
@Composable
fun AccountRowItem(
    logo: Int,
    name: String,
    number: String,
    type: String,
    isSelected: Boolean? = false,
    onSelected: () -> Unit = {},
    onDeleted: () -> Unit = {}
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelected()
            }
    ){
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            if(type == "select"){
                if(isSelected!!){
                    Log.d(TAG, "AccountRowItem isSelected true")
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(color = PinkDarkest, shape = CircleShape)
                            .border(1.dp, DarkGray, shape = CircleShape)
                    )
                }else{
                    Log.d(TAG, "AccountRowItem isSelected false")
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(color = Color.Transparent, shape = CircleShape)
                            .border(1.dp, DarkGray, shape = CircleShape)
                            .clickable {
                                onSelected()
                            }
                    )
                }
            }
            Image(
                painter = painterResource(id = logo),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Column {
                Text(
                    text = name,
                    style = CustomTextStyle.pretendardMedium12
                )
                Text(
                    text = number,
                    style = CustomTextStyle.pretendardMedium12
                )

            }

        }
        if(type=="delete"){
            Text(
                text = "삭제",
                style = CustomTextStyle.pretendardMedium12,
                color = PinkDarkest,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onDeleted()
                    }
            )

        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AccountRowItemPreview(){
    AccountRowItem(
        logo = 0,
        name = "신한",
        number = "997838829102",
        type = "delete"
    )
}