package com.moneyminions.domain.repository.example

import com.moneyminions.domain.model.example.ExampleDto

interface ExampleRepository {
    suspend fun getUserRepos(user: String): List<ExampleDto>
}