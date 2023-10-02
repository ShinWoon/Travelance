package com.moneyminions.domain.usecase.home

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.home.AnnouncementDto
import com.moneyminions.domain.repository.home.HomeRepository
import javax.inject.Inject

class EditAnnouncementUseCase @Inject constructor(
    private val homeRepository: HomeRepository
){
    suspend operator fun invoke(roomId: Int, announcementDto: AnnouncementDto): NetworkResult<CommonResultDto> {
        return homeRepository.editAnnouncement(roomId = roomId, announcementDto = announcementDto)
    }
}