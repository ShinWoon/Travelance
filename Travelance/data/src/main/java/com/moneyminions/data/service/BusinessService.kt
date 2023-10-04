package com.moneyminions.data.service

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.home.request.AnnouncementEditRequest
import com.moneyminions.data.model.home.request.AnnouncementRequest
import com.moneyminions.data.model.home.request.UseCashRequest
import com.moneyminions.data.model.home.response.AnnouncementResponse
import com.moneyminions.data.model.home.response.TravelRoomFriendsResponse
import com.moneyminions.data.model.home.response.TravelRoomInfoResponse
import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.request.MemberInfoRequest
import com.moneyminions.data.model.login.response.AccountResponse
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.CardResponse
import com.moneyminions.data.model.login.response.JoinResponse
import com.moneyminions.data.model.login.response.LoginResponse
import com.moneyminions.data.model.login.response.MemberInfoResponse
import com.moneyminions.data.model.login.response.ReAccessTokenResponse
import com.moneyminions.data.model.traveldetail.request.FinalPaymentRequest
import com.moneyminions.data.model.traveldetail.response.SettleResultResponse
import com.moneyminions.data.model.traveldetail.request.PaymentCompleteRequest
import com.moneyminions.data.model.traveldetail.request.TravelPaymentChangeInfoRequest
import com.moneyminions.data.model.traveldetail.response.TravelDetailInfoResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailMyPaymentResponse
import com.moneyminions.data.model.traveldone.response.TravelDoneTotalInfoResponse
import com.moneyminions.data.model.traveledit.request.TravelRoomEditRequest
import com.moneyminions.data.model.travellist.request.RoomInfoRequestDto
import com.moneyminions.data.model.travellist.request.RoomUserRequestDto
import com.moneyminions.data.model.travellist.response.TravelRoomResponse
import com.moneyminions.data.model.travelmap.request.TravelMapDetailRequest
import com.moneyminions.data.model.travelmap.response.TravelMapDetailResponse
import com.moneyminions.data.model.travelmap.response.TravelMapInfoResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface BusinessService {

    /**
     * 로그인 API
     */
    @POST("api/oauth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    /**
     * 1원 이체 요청
     */
    @POST("account/1request")
    suspend fun postAuthenticationAccount(@Body authenticationAccountRequest: AuthenticationAccountRequest): AuthenticationAccountResponse

    /**
     * 1원 이체 확인
     */
    @POST("account/1response")
    suspend fun confirmAuthenticationAccount(@Body authenticationAccountRequest: AuthenticationAccountRequest): CommonResponse

    /**
     * 내 전체 계좌 조회
     */
    @POST("account/allaccount")
    suspend fun getAccountList(): List<AccountResponse>

    /**
     * 내 전체 카드 조회
     */
    @POST("card/allcard")
    suspend fun getCardList(): List<CardResponse>

    /**
     * 회원 등록
     */
    @PATCH("member/additional")
    suspend fun join(@Body memberInfoRequest: MemberInfoRequest): JoinResponse

    /**
     * 여행방 만들기 API
     */
    @Multipart
    @POST("/travel/room")
    suspend fun createTravelRoom(
        @Part imageFiles: MultipartBody.Part?,
        @Part("roomUserRequestDto") roomUserRequestDto: RoomUserRequestDto,
        @Part("roomInfoRequestDto") roomInfoRequestDto: RoomInfoRequestDto,
    ): CommonResponse

    /**
     * 여행 목록 요청 API
     */
    @GET("travel/room")
    suspend fun travelList(): MutableList<TravelRoomResponse>

    /**
     * 마이페이지 조회
     */
    @POST("member/mypage")
    suspend fun getMemberInfo(): MemberInfoResponse

    /**
     * 닉네임 수정
     */
    @PATCH("member/update/nickname")
    suspend fun updateNickname(@Query("nickname") nickname: String): CommonResponse

    /**
     * ACCESS 토큰 재발급
     */
    @POST("api/accounts/access-token/re")
    suspend fun postReAccessToken(@Header("Authorization") refreshToken: String): Response<ReAccessTokenResponse>

    /**
     * 여행방 삭제 API
     */
    @DELETE("travel/room/{roomId}")
    suspend fun deleteTravelRoom(@Path(value = "roomId") roomId: Int): CommonResponse


    /**
     * 개인 결제 금액 요청
     */
    @GET("travel/payment/{roomId}/alone")
    suspend fun getMyPaymentList(@Path(value = "roomId") roomId: Int): List<TravelDetailMyPaymentResponse>

    /**
     * 여행 상세 요청
     */
    @GET("travel/payment/{roomId}/with")
    suspend fun getTravelDetailInfo(@Path(value = "roomId") roomId: Int): TravelDetailInfoResponse

    /**
     * 공금 여부 수정
     */
    @POST("payment/push/alert")
    suspend fun updatePaymentInfo(@Body travelPaymentChangeInfoRequest: TravelPaymentChangeInfoRequest): CommonResponse

    /**
     * 여행 정산 요청
     */
    @POST("payment/complete")
    suspend fun setSettleState(@Body paymentCompleteRequest: PaymentCompleteRequest): CommonResponse

    /**
     * 여행방 생성 API
     */
    @POST("travel/room/{roomId}/start")
    suspend fun startTravel(@Path(value = "roomId") roomId: Int): CommonResponse

    /**
     * 특정 여행방 조회 API
     */
    @GET("travel/room/{roomId}")
    suspend fun getTravelRoomInfo(@Path(value = "roomId") roomId: Int): TravelRoomInfoResponse

    /**
     * 현급 입력 API
     */
    @POST("payment/cash")
    suspend fun requestUseCash(@Body useCashRequest: UseCashRequest): CommonResponse

    /**
     * 여행방 친구 조회
     */
    @GET("travel/room/{roomId}/UserList")
    suspend fun getTravelRoomFriends(@Path("roomId") roomId: Int): List<TravelRoomFriendsResponse>
    
    /**
     * 공지사항 등록
     */
    @POST("notice/save")
    suspend fun saveAnnouncement(@Body announcementRequest: AnnouncementRequest): CommonResponse

    /**
     * 공지사항 전체 조회
     */
    @GET("notice/{roomId}")
    suspend fun requestAnnouncementList(@Path(value = "roomId") roomId: Int): List<AnnouncementResponse>

    /**
     * 공지사항 삭제
     */
    @DELETE("notice/{roomId}/{noticeId}")
    suspend fun deleteAnnouncement(
        @Path(value = "roomId") roomId: Int,
        @Path(value = "noticeId") noticeId: Int,
    ): CommonResponse
    
    /**
     * 공지사항 수정
     */
    @PATCH("notice/{roomId}")
    suspend fun editAnnouncement(
        @Path(value = "roomId") roomId: Int,
        @Body announcementEditRequest: AnnouncementEditRequest
    ): CommonResponse


    /**
     * 여행방 수정
     */
    @PATCH("travel/room/{roomId}")
    suspend fun editTravelRoom(
        @Path("roomId") roomId: Int,
        @Body travelRoomEditRequest: TravelRoomEditRequest
    ): CommonResponse
    /**
     * 카드 삭제
     */
    @DELETE("card/delete/{cardCoName}/{cardNumber}")
    suspend fun deleteCard(@Path("cardCoName")cardName: String, @Path("cardNumber") cardNumber: String): CommonResponse
    /**
     * 계좌 삭제
     */
    @DELETE("account/delete/{accountName}/{account}")
    suspend fun deleteAccount(@Path("accountName") accountName: String, @Path("account") accountNumber: String): CommonResponse
    /**
     * 카드, 계좌 추가 등록
     */
    @POST("member/add")
    suspend fun addAccountAndCard(@Body memberInfoRequest: MemberInfoRequest): CommonResponse

    /**
     * 공금 여부 수정(fcm용)
     */
    @POST("payment/push/alert")
    suspend fun updateFCMPaymentInfo(@Body travelPaymentChangeInfoRequest: TravelPaymentChangeInfoRequest): Response<CommonResponse>

    /**
     * 여행 장소들 조회
     */
    @GET("travel/map/{roomId}")
    suspend fun getTravelSpots(@Path("roomId") roomId: Int): List<TravelMapInfoResponse>

    /**
     * 여행 장소 상세 조회
     */
    @POST("travel/map/{roomId}/detail")
    suspend fun getTravelSpotDetail(
        @Path("roomId") roomId: Int,
        @Body travelMapDetailRequest: TravelMapDetailRequest
    ) : List<TravelMapDetailResponse>

    /**
     * 여행 완료 방 조회
     */
    @POST("travel/payment/{roomId}/travelDone")
    suspend fun getTravelDoneInfo(@Path("roomId") roomId: Int): TravelDoneTotalInfoResponse


    /**
     * 정산 결과 조회
     */
    @GET("payment/{roomId}/info")
    suspend fun getSettleResult(
        @Path("roomId") roomId: Int
    ): SettleResultResponse

    /**
     * 로그아웃
     */
    @POST("api/oauth/logout")
    suspend fun logout(): CommonResponse
    /**
     * 회원탈퇴
     */
    @DELETE("member/delete")
    suspend fun joinOut(): CommonResponse

    /**
     * 초대받은 참가자 -> 여행 참가
     */
    @Multipart
    @POST("travel/room/{roomId}/addUser")
    suspend fun requestJoinUser(
        @Path("roomId") roomId: Int,
        @Part imageFiles: MultipartBody.Part?,
        @Part("roomUserRequestDto") roomUserRequestDto: RoomUserRequestDto,
    ): CommonResponse

    /**
     * 최종 이체
     */
    @POST("payment/transfer")
    suspend fun postFinalPayment(
        @Body finalPaymentRequest: FinalPaymentRequest
    ): CommonResponse

}