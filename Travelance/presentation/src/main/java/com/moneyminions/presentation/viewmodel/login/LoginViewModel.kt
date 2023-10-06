package com.moneyminions.presentation.viewmodel.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kakao.sdk.user.UserApiClient
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.model.login.LoginResultDto
import com.moneyminions.domain.usecase.login.JoinUseCase
import com.moneyminions.domain.usecase.login.LoginUseCase
import com.moneyminions.domain.usecase.preference.GetFCMTokenUseCase
import com.moneyminions.domain.usecase.preference.GetJwtTokenUseCase
import com.moneyminions.domain.usecase.preference.GetRoleUseCase
import com.moneyminions.domain.usecase.preference.PutFCMTokenUseCase
import com.moneyminions.domain.usecase.preference.PutJwtTokenUseCase
import com.moneyminions.domain.usecase.preference.PutRoleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.firebase.messaging.FirebaseMessaging

private const val TAG = "LoginViewModel D210"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val putJwtTokenUseCase: PutJwtTokenUseCase,
    private val putRoleUseCase: PutRoleUseCase,
    private val getJwtTokenUseCase: GetJwtTokenUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val loginUseCase: LoginUseCase,
    private val joinUseCase: JoinUseCase,
    private val putFCMTokenUseCase: PutFCMTokenUseCase,
    private val getFCMTokenUseCase: GetFCMTokenUseCase
): ViewModel() {

    private val _loginResult = MutableStateFlow<NetworkResult<LoginResultDto>>(NetworkResult.Idle)
    val loginResult = _loginResult.asStateFlow()

    fun singInKakao(context: Context) {
        val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.d(TAG, "callback...")
            when{
                error != null -> Log.e(TAG, "kakao login error 2 $error")
                token != null -> {
                    Log.d(TAG, "kakao login success -> access Token : ${token.accessToken}")
                    Log.d(TAG, "kakao login success -> refresh Token : ${token.refreshToken}")
                    updateJwtToken(token.accessToken, token.refreshToken, null)
//                    putFCMTokenUseCase.invoke()
                    FirebaseMessaging.getInstance().token.addOnCompleteListener {
                        if (!it.isSuccessful) {
                            // 토큰 요청 task가 실패 한 경우 처리
                            return@addOnCompleteListener
                        }
                        // 토큰 요청 task가 성공한 경우 task의 result에 token 값이 내려온다.
                        putFCMTokenUseCase.invoke(it.result)
                        Log.d(TAG, "preference의 fcmTOKEN : ${getFCMTokenUseCase.invoke()}")
                        //로그인 api 호출
                        viewModelScope.launch {
                            _loginResult.emit(loginUseCase.invoke("KAKAO"))
                        }
                    }
                }
            }
        }

        Log.d(TAG, "singInKakao....")
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(context)){
            //카카오톡 설치 시
            UserApiClient.instance.loginWithKakaoTalk(context){ token, error ->
                if(error != null){
                    Log.e(TAG, "kakao login error 1")
                }
                if(error is ClientError && error.reason == ClientErrorCause.Cancelled){
                    return@loginWithKakaoTalk
                }
                UserApiClient.instance.loginWithKakaoAccount(
                    context = context,
                    callback = kakaoCallback
                )
            }
        }else{
            //미설치 시
            UserApiClient.instance.loginWithKakaoAccount(
                context = context,
                callback = kakaoCallback
            )
        }
    }

    fun updateJwtToken(accessToken: String?, refreshToken: String?, role: String?){
        putJwtTokenUseCase.invoke(JwtTokenDto(accessToken, refreshToken, role))
    }

//    fun updateRole(role: String?){
//        putRoleUseCase.invoke(role)
//    }

    fun getJwtToken(): JwtTokenDto{
        return getJwtTokenUseCase.invoke()
    }

//    fun getRole(): String{
//        return getRoleUseCase.invoke()
//    }

    suspend fun refreshNetworkState(){
        _loginResult.emit(NetworkResult.Idle)
    }

    val memberInfo: MemberInfo = MemberInfo(listOf(), listOf(), "", "")
    fun setMemberAccountList(list: List<AccountDto>){
        memberInfo.accountList = list
    }
    fun setMemberCardList(list: List<CardDto>){
        memberInfo.cardList = list
    }
    fun setNickname(nickname: String){
        memberInfo.nickname = nickname
    }
    fun setPassword(password: String){
        memberInfo.password = password
    }

    private val _joinResult = MutableStateFlow<NetworkResult<JwtTokenDto>>(NetworkResult.Idle)
    val joinResult = _joinResult.asStateFlow()
    fun join(){
        viewModelScope.launch {
            Log.d(TAG, "최종 로그인 바디에  : $memberInfo")
            _joinResult.emit(joinUseCase.invoke(memberInfo))
        }
    }

}