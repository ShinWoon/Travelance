package com.moneyminions.data.repository.home

import com.moneyminions.data.datasource.remote.home.HomeDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
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

    override suspend fun getTravelRoomFriends(roomId: Int): NetworkResult<List<TravelRoomFriendDto>> {
        return handleApi { homeDataSource.getTravelRoomFriends(roomId).map { it.toDomain() } }
    }
}