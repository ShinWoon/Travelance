package com.moneyminions.data.datasource.remote.example

import com.moneyminions.data.model.example.ExampleResponse
import com.moneyminions.data.service.example.ExampleService
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult

class ExampleDataSourceImpl(
    private val ExampleService: ExampleService
): ExampleDataSource{
    override suspend fun getUserRepos(user: String): NetworkResult<List<ExampleResponse>> {
        return handleApi { ExampleService.getUserRepos(user) }
    }
}