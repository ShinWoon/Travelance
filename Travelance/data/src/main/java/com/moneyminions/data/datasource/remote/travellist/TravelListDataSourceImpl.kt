package com.moneyminions.data.datasource.remote.travellist

import android.util.Log
import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.model.travellist.response.TravelRoomResponse
import com.moneyminions.data.service.BusinessService


private const val TAG = "TravelListDataSourceImpl_D210"
class TravelListDataSourceImpl(
    private val businessService: BusinessService
): TravelListDataSource {

    /**
     * 여행 방 생성
     */
    override suspend fun createTravelRoom(createTravelRoomRequest: CreateTravelRoomRequest): CommonResponse {
        Log.d(TAG, "createTravelRoom 끝단: " +
                "\n ${createTravelRoomRequest.imageFiles?.body} \n ${createTravelRoomRequest.roomUserRequestDto} \n ${createTravelRoomRequest.roomInfoRequestDto} ")
        
        if(createTravelRoomRequest.imageFiles == null) {
            Log.d(TAG, "createTravelRoom: 이미지 파일이 null이여!!!~~~!!!!")
        }
        
        return businessService.createTravelRoom (
            createTravelRoomRequest.imageFiles,
            createTravelRoomRequest.roomUserRequestDto,
            createTravelRoomRequest.roomInfoRequestDto,
        )
    }

    /**
     * 여행방 목록 조회
     */
    override suspend fun getTravelList(): List<TravelRoomResponse> {
        return businessService.travelList()
    }

    /**
     * 여행방 삭제
     */
    override suspend fun deleteTravelRoom(roomId: Int): CommonResponse {
        return businessService.deleteTravelRoom(roomId = roomId)
    }
}