package com.moneyminions.data.repository.example

import com.moneyminions.data.datasource.remote.example.ExampleDataSource
import com.moneyminions.data.mapper.example.toDomain
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.example.ExampleDto
import com.moneyminions.domain.repository.example.ExampleRepository
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(
    private val ExampleDataSource: ExampleDataSource
): ExampleRepository {
    override suspend fun getUserRepos(user: String): List<ExampleDto> {
        return when (val result = ExampleDataSource.getUserRepos(user)) {
            is NetworkResult.Success -> {
                result.data.map { it.toDomain() }
            }
            else -> {
                listOf()
            }
        }
//        return handleApi { ExampleDataSource.getUserRepos(user).map { it.toDomain() }
    }

}