package com.moneyminions.data.mapper

import com.moneyminions.data.datasource.remote.common.response.CommonResponse
import com.moneyminions.data.model.common.response.AccountResponse
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CommonResultDto

fun CommonResponse.toDomain(): CommonResultDto{
    return CommonResultDto(
        result = result
    )
}

fun AccountResponse.toDomain(): AccountDto {
    return AccountDto(
        idx = idx,
        bankName = bankName,
        accountNumber = account
    )
}