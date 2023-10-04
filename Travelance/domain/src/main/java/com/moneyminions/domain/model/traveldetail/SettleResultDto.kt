package com.moneyminions.domain.model.traveldetail

data class SettleResultDto(
    val paymentInfo: SettleResultPaymentInfoDto,
    val travelRoomInfo: SettleResultRoomInfoDto,
    val receiveInfos: List<SettleResultUserInfoDto>,
    val sendInfos: List<SettleResultUserInfoDto>
)
