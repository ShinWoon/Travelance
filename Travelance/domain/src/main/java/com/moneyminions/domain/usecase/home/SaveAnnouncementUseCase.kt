package com.moneyminions.domain.usecase.home

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.home.AnnouncementDto
import com.moneyminions.domain.repository.home.HomeRepository
import javax.inject.Inject

class SaveAnnouncementUseCase @Inject constructor(
    private val homeRepository: HomeRepository
){
    suspend operator fun invoke(announcementDto: AnnouncementDto): NetworkResult<CommonResultDto> {
        return homeRepository.saveAnnouncement(announcementDto = announcementDto)
    }
}