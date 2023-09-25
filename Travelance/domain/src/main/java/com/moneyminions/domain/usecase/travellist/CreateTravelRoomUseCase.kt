package com.moneyminions.domain.usecase.travellist

import android.util.Log
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.repository.travellist.TravelListRepository
import javax.inject.Inject


private const val TAG = "CreateTravelRoomUseCase_D210"
class CreateTravelRoomUseCase @Inject constructor(
    private val travelListRepository: TravelListRepository
){
    suspend operator fun invoke(travelRoomDto: TravelRoomDto) : NetworkResult<CommonResultDto>{
        Log.d(TAG, "invoke: $travelRoomDto")
        return travelListRepository.createTravelRoom(travelRoomDto)
    }
}