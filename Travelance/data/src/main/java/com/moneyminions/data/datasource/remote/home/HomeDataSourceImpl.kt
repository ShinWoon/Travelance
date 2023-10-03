package com.moneyminions.data.datasource.remote.home

import android.util.Log
import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.home.request.AnnouncementEditRequest
import com.moneyminions.data.model.home.request.AnnouncementRequest
import com.moneyminions.data.model.home.request.UseCashRequest
import com.moneyminions.data.model.home.response.AnnouncementResponse
import com.moneyminions.data.model.home.response.TravelRoomFriendsResponse
import com.moneyminions.data.model.home.response.TravelRoomInfoResponse
import com.moneyminions.data.service.BusinessService

private const val TAG = "HomeDataSourceImpl_D210"
class HomeDataSourceImpl(
    private val businessService: BusinessService,
) : HomeDataSource {
    
    /**
     * 여행방 시작
     */
    override suspend fun startTravel(roomId: Int): CommonResponse {
        return businessService.startTravel(roomId = roomId)
    }
    
    /**
     * 특정 여행방 조회
     */
    override suspend fun getSelectTravelRoom(roomId: Int): TravelRoomInfoResponse {
        return businessService.getTravelRoomInfo(roomId = roomId)
    }
    
    /**
     * 현금 사용 등록
     */
    override suspend fun requestUseCash(useCashRequest: UseCashRequest): CommonResponse {
        return businessService.requestUseCash(useCashRequest = useCashRequest)
    }
    
    /**
     * 여행 친구 목록 조회
     */
    override suspend fun getTravelRoomFriends(roomId: Int): List<TravelRoomFriendsResponse> {
        Log.d(TAG, "getTravelRoomFriends: $roomId")
        return businessService.getTravelRoomFriends(roomId = roomId)
    }
    
    /**
     * 공지사항 등록
     */
    override suspend fun saveAnnouncement(announcementRequest: AnnouncementRequest): CommonResponse {
        return businessService.saveAnnouncement(announcementRequest = announcementRequest)
    }
    
    /**
     * 공지사항 전체 조회
     */
    override suspend fun requestAnnouncementList(roomId: Int): List<AnnouncementResponse> {
        return businessService.requestAnnouncementList(roomId = roomId)
    }
    
    /**
     * 공지사항 삭제
     */
    override suspend fun deleteAnnouncement(
        roomId: Int,
        noticeId: Int,
    ): CommonResponse {
        Log.d(TAG, "deleteAnnouncement: $roomId, $noticeId")
        return businessService.deleteAnnouncement(
            roomId = roomId,
            noticeId = noticeId,
        )
    }
    
    /**
     * 공지사항 수정
     */
    override suspend fun editAnnouncement(
        roomId: Int,
        announcementEditRequest: AnnouncementEditRequest,
    ): CommonResponse {
        Log.d(TAG, "editAnnouncement: $roomId \n $announcementEditRequest")
        return businessService.editAnnouncement(
            roomId = roomId,
            announcementEditRequest = announcementEditRequest
        )
    }
}