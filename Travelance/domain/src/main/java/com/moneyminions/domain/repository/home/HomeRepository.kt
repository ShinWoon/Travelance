package com.moneyminions.domain.repository.home

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.home.AnnouncementDto
import com.moneyminions.domain.model.home.AnnouncementEditDto
import com.moneyminions.domain.model.home.CashDto
import com.moneyminions.domain.model.home.TravelRoomFriendDto
import com.moneyminions.domain.model.home.TravelRoomInfoDto

interface HomeRepository {
    /**
     * 여행방 시작
     */
    suspend fun startTravel(roomId: Int): NetworkResult<CommonResultDto>
    
    /**
     * 특정 여행방 조회
     */
    suspend fun getTravelRoomInfo(roomId: Int): NetworkResult<TravelRoomInfoDto>
    
    /**
     * 현급 입력
     */
    suspend fun requestUseCash(cashDto: CashDto): NetworkResult<CommonResultDto>
    
    /**
     * 여행 친구 조회
     */
    suspend fun getTravelRoomFriends(roomId: Int): NetworkResult<List<TravelRoomFriendDto>>
    
    /**
     * 공지사항 등록
     */
    suspend fun saveAnnouncement(announcementDto: AnnouncementDto): NetworkResult<CommonResultDto>
    
    /**
     * 공지사항 전체 조회
     */
    suspend fun requestAnnouncementList(roomId: Int): NetworkResult<List<AnnouncementDto>>
    
    /**
     * 공지사항 삭제
     */
    suspend fun deleteAnnouncement(roomId: Int, noticeId: Int): NetworkResult<CommonResultDto>
    
    /**
     * 공지사항 수정
     */
    suspend fun editAnnouncement(
        roomId: Int,
        announcementEditDto: AnnouncementEditDto,
    ): NetworkResult<CommonResultDto>
}