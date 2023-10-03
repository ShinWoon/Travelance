package com.moneyminions.data.mapper

import com.moneyminions.data.model.travelmap.request.TravelMapDetailRequest
import com.moneyminions.data.model.travelmap.response.TravelMapDetailResponse
import com.moneyminions.data.model.travelmap.response.TravelMapInfoResponse
import com.moneyminions.domain.model.travelmap.TravelMapDetailDto
import com.moneyminions.domain.model.travelmap.TravelMapSpotDto
import com.moneyminions.domain.model.travelmap.TravelMapStoreAddressDto

fun TravelMapStoreAddressDto.toData(): TravelMapDetailRequest {
    return TravelMapDetailRequest(
        storeAddress = storeAddress,
    )
}

fun TravelMapDetailResponse.toDomain(): TravelMapDetailDto{
    return TravelMapDetailDto(
        address = address,
        category = category,
        content = content,
        nickName = nickName,
        paymentAt = paymentAt,
        price= price,
    )
}

fun TravelMapInfoResponse.toDomain(): TravelMapSpotDto {
    return TravelMapSpotDto(
        storeAddress = storeAddress,
        storeSector = storeSector,
    )
}