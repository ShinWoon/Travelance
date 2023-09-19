package com.moneyminions.presentation.viewmodel.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.ItemContent
import com.kakao.sdk.template.model.Link
import javax.inject.Inject

private const val TAG = "HomeViewModel"
class HomeViewModel @Inject constructor(

): ViewModel(){
    // 유저가 프로필 정보를 등록했는지 유무 체크
    private val _isUserInfo = mutableStateOf(false)
    val isUserInfo: State<Boolean> = _isUserInfo
    fun setUserInfo(check: Boolean) {
        _isUserInfo.value = check
    }
    
    // 여행 시작 유무 판단
    private val _isTravelStart = mutableStateOf(false)
    val isTravelStart: State<Boolean> = _isTravelStart
    fun setTravelStart(check: Boolean) {
        _isTravelStart.value = check
    }
    
    
    /**
     *  카카오 링크 초대
     */
    fun sendKakaoLink(context: Context) { // todo 유저 객체 & 여행방 정보 받아서 전송해야함
        /**
         * 템플릿 생성
         */
        val defaultFeed = FeedTemplate(
            content = Content(
                title = "여행방 이름", // 여행방 이름
                imageUrl = "https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                link = Link(
                    androidExecutionParams = mapOf("type" to "6", "route" to "main", "data" to "data"),
                    iosExecutionParams = mapOf("type" to "6", "route" to "main", "data" to "data")
                )
            ),
            itemContent = ItemContent(
                // 초대자 이름, 프로필 이미지
                profileText = "초대자 이름",
                profileImageUrl = "https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
            ),
            buttons = listOf(
                Button(
                    "참여 하기",
                    Link(
                        //
                        androidExecutionParams = mapOf("number" to "6", "route" to "main", "data" to "data"),
                        iosExecutionParams = mapOf("number" to "6", "route" to "main", "data" to "data"),
                    )
                )
            )
        )
    
        /**
         * 카카오톡 설치여부 확인
         */
        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, error ->
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
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)
            
            // CustomTabs으로 웹 브라우저 열기
            val customTabsIntent = CustomTabsIntent.Builder().build()
            
            // CustomTabsServiceConnection 지원 브라우저 열기 (ex. Chrome, 삼성 인터넷, FireFox, 웨일 등)
            try {
                customTabsIntent.launchUrl(context, sharerUrl)
                Log.d(TAG, "카카오톡 미설치 - openWithDefault")
            } catch (e: UnsupportedOperationException) {
                // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
                // 인터넷 브라우저가 없을 경우 구글 플레이 스토어 홈으로 이동
                val googlePlayHomeUrl = "https://play.google.com/store"
                val googlePlayHomeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(googlePlayHomeUrl))
                context.startActivity(googlePlayHomeIntent)
            }
            
            // CustomTabsServiceConnection 미지원 브라우저 열기 (ex. 다음, 네이버 등)
            try {
                KakaoCustomTabsClient.open(context, sharerUrl)
                Log.d(TAG, "카카오톡 미설치 - open")
            } catch (e: ActivityNotFoundException) {
                // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
                // 인터넷 브라우저가 없을 경우 구글 플레이 스토어 홈으로 이동
                val googlePlayHomeUrl = "https://play.google.com/store"
                val googlePlayHomeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(googlePlayHomeUrl))
                context.startActivity(googlePlayHomeIntent)
            }
        }
        
    }

}