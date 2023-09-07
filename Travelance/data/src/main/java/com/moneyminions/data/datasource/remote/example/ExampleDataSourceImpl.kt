package com.moneyminions.data.datasource.remote.example

import com.moneyminions.data.model.NetworkResult
import com.moneyminions.data.model.response.example.ExampleResponse
import com.moneyminions.data.service.example.ExampleService
import com.moneyminions.data.service.handleApi

class ExampleDataSourceImpl(
    private val ExampleService: ExampleService
): ExampleDataSource{
    override suspend fun getUserRepos(user: String): NetworkResult<List<ExampleResponse>> {
        return handleApi { ExampleService.getUserRepos(user) }
    }
}