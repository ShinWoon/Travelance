package com.moneyminions.domain.usecase.home

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.home.TravelRoomFriendDto
import com.moneyminions.domain.repository.home.HomeRepository
import javax.inject.Inject

class GetTravelRoomFriendsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(roomId: Int) : NetworkResult<List<TravelRoomFriendDto>> {
        return homeRepository.getTravelRoomFriends(roomId)
    }
}