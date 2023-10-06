package com.moneyminions.domain.usecase.example

import com.moneyminions.domain.model.example.ExampleDto
import com.moneyminions.domain.repository.example.ExampleRepository
import javax.inject.Inject

class ExampleUseCase @Inject constructor(
    private val ExampleRepository: ExampleRepository
){
    //    suspend operator fun invoke(user: String): List<ExampleDto> {
//        return when (val result = ExampleRepository.getUserRepos(user)) {
//            is NetworkResult.Success -> {
//                Log.d(TAG, "success -> ${result.data}")
//                result.data
//            }
//            else -> {
//                Log.d(TAG, "fail...")
//                listOf()
//            }
//        }
//    }
    suspend operator fun invoke(user: String): List<ExampleDto>{
        return ExampleRepository.getUserRepos(user)
    }
}