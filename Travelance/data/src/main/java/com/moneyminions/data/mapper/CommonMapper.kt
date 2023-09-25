package com.moneyminions.data.mapper

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.login.response.AccountResponse
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CommonResultDto

fun CommonResponse.toDomain(): CommonResultDto{
    return CommonResultDto(
        result = result
    )
}

