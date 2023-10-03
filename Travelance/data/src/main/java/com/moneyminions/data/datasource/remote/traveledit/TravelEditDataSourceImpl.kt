package com.moneyminions.data.datasource.remote.traveledit

import android.util.Log
import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.traveledit.request.TravelRoomEditRequest
import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.service.BusinessService

private const val TAG = "TravelEditDataSourceImp_D210"
class TravelEditDataSourceImpl(
    private val businessService: BusinessService
) : TravelEditDataSource {
    override suspend fun editTravelRoom(
        roomId: Int,
        travelRoomEditRequest: TravelRoomEditRequest
    ): CommonResponse {
        return businessService.editTravelRoom(
            roomId = roomId,
            travelRoomEditRequest = travelRoomEditRequest
        )
    }

    /**
     * 초대받은 참가자 -> 여행 참가
     */
    override suspend fun requestJoinUser(
        roomId: Int,
        createTravelRoomRequest: CreateTravelRoomRequest
    ): CommonResponse {
        Log.d(TAG, "requestJoinUser 끝단: $roomId \n ${createTravelRoomRequest.imageFiles} \n ${createTravelRoomRequest.roomUserRequestDto}")
        return businessService.requestJoinUser(
            roomId = roomId,
            imageFiles = createTravelRoomRequest.imageFiles,
            roomUserRequestDto = createTravelRoomRequest.roomUserRequestDto,
        )
    }
}