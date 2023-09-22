package com.moneyminions.data.datasource.remote.example

import com.moneyminions.data.model.example.ExampleResponse
import com.moneyminions.domain.model.NetworkResult

interface ExampleDataSource {
    suspend fun getUserRepos(user: String): NetworkResult<List<ExampleResponse>>
}