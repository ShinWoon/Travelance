package com.moneyminions.data.mapper.example

import com.moneyminions.data.model.example.ExampleResponse
import com.moneyminions.domain.model.example.ExampleDto

fun ExampleResponse.toDomain(): ExampleDto {
    return ExampleDto(
        id, name, htmlUrl, url, gitUrl
    )
}