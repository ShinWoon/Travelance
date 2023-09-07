package com.moneyminions.data.datasource.remote.example

import com.moneyminions.data.model.NetworkResult
import com.moneyminions.data.model.response.example.ExampleResponse

interface ExampleDataSource {
    suspend fun getUserRepos(user: String): NetworkResult<List<ExampleResponse>>
}