package com.moneyminions.domain.usecase.home

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.home.AnnouncementDto
import com.moneyminions.domain.repository.home.HomeRepository
import javax.inject.Inject

class GetAnnouncementListUseCase @Inject constructor(
    private val homeRepository: HomeRepository
){
    suspend operator fun invoke(roomId: Int): NetworkResult<List<AnnouncementDto>> {
        return homeRepository.requestAnnouncementList(roomId = roomId)
    }
}