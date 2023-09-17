package com.moneyminions.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.compose.rememberNavController
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.TextTemplate
import com.moneyminions.presentation.screen.MainScreen
import com.moneyminions.presentation.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity D210"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                MainScreen(rememberNavController())

//                var keyHash = Utility.getKeyHash(this)
//                Log.d(TAG, "Kakao HashKey : $keyHash")

                sendKakaoLink(this)
            }
        }
    }

}
// 카카오 링크 초대
fun sendKakaoLink(context: Context) {
    val defaultText = TextTemplate(
        text = """
        카카오톡 공유는 카카오톡을 실행하여
        사용자가 선택한 채팅방으로 메시지를 전송합니다.
    """.trimIndent(),
        link = Link(
            webUrl = "https://developers.kakao.com",
            mobileWebUrl = "https://developers.kakao.com"
        )
    )
    // 카카오톡 설치여부 확인
    if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
        // 카카오톡으로 카카오톡 공유 가능
        ShareClient.instance.shareDefault(context, defaultText) { sharingResult, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡 공유 실패", error)
            } else if (sharingResult != null) {
                Log.d(TAG, "카카오톡 공유 성공 ${sharingResult.intent}")
                context.startActivity(sharingResult.intent)

                // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                Log.w(TAG, "Warning Msg: ${sharingResult.warningMsg}")
                Log.w(TAG, "Argument Msg: ${sharingResult.argumentMsg}")
            }
        }
    } else {
        // 카카오톡 미설치: 웹 공유 사용 권장
        // 웹 공유 예시 코드
        val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultText)

        // CustomTabs으로 웹 브라우저 열기

        // 1. CustomTabsServiceConnection 지원 브라우저 열기
        // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
        try {
            KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
        } catch (e: UnsupportedOperationException) {
            // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
        }

        // 2. CustomTabsServiceConnection 미지원 브라우저 열기
        // ex) 다음, 네이버 등
        try {
            KakaoCustomTabsClient.open(context, sharerUrl)
        } catch (e: ActivityNotFoundException) {
            // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
        }
    }
}
