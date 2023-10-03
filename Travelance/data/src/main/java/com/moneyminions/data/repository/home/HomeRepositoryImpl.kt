package com.moneyminions.data.repository.home

import com.moneyminions.data.datasource.remote.home.HomeDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.home.AnnouncementDto
import com.moneyminions.domain.model.home.AnnouncementEditDto
import com.moneyminions.domain.model.home.CashDto
import com.moneyminions.domain.model.home.TravelRoomFriendDto
import com.moneyminions.domain.model.home.TravelRoomInfoDto
import com.moneyminions.domain.repository.home.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    val homeDataSource: HomeDataSource
): HomeRepository {
    /**
     * 여행방 시작
     */
    override suspend fun startTravel(roomId: Int): NetworkResult<CommonResultDto> {
        return handleApi { homeDataSource.startTravel(roomId).toDomain() }
    }
    
    /**
     * 특정 여행방 조회
     */
    override suspend fun getTravelRoomInfo(roomId: Int): NetworkResult<TravelRoomInfoDto> {
        return handleApi { homeDataSource.getSelectTravelRoom(roomId).toDomain() }
    }
    
    /**
     * 현급 등록
     */
    override suspend fun requestUseCash(cashDto: CashDto): NetworkResult<CommonResultDto> {
        return handleApi { homeDataSource.requestUseCash(cashDto.toData()).toDomain() }
    }
    
    /**
     * 여행 친구 목록 조회
     */
    override suspend fun getTravelRoomFriends(roomId: Int): NetworkResult<List<TravelRoomFriendDto>> {
        return handleApi { homeDataSource.getTravelRoomFriends(roomId).map { it.toDomain() } }
    }
    
    /**
     * 공지사항 등록
     */
    override suspend fun saveAnnouncement(announcementDto: AnnouncementDto): NetworkResult<CommonResultDto> {
        return handleApi { homeDataSource.saveAnnouncement(announcementRequest = announcementDto.toData()).toDomain() }
    }
    
    /**
     * 공지사항 전체 조회
     */
    override suspend fun requestAnnouncementList(roomId: Int): NetworkResult<List<AnnouncementDto>> {
        return handleApi { homeDataSource.requestAnnouncementList(roomId = roomId).map { it.toDomain() } }
    }
    
    /**
     * 공지사항 삭제
     */
    override suspend fun deleteAnnouncement(
        roomId: Int,
        noticeId: Int,
    ): NetworkResult<CommonResultDto> {
        return handleApi { homeDataSource.deleteAnnouncement(roomId = roomId, noticeId = noticeId).toDomain() }
    }
    
    /**
     * 공지사항 수정
     */
    override suspend fun editAnnouncement(
        roomId: Int,
        announcementEditDto: AnnouncementEditDto,
    ): NetworkResult<CommonResultDto> {
        return handleApi { homeDataSource.editAnnouncement(roomId = roomId, announcementEditRequest = announcementEditDto.toData()).toDomain() }
    }
}