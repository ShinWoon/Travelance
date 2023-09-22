package com.moneyminions.data.mapper

import com.moneyminions.data.datasource.remote.common.response.CommonResponse
import com.moneyminions.domain.model.common.CommonResultDto

fun CommonResponse.toDomain(): CommonResultDto{
    return CommonResultDto(
        result = result
    )
}