package com.moneyminions.data.service.example

import com.moneyminions.data.model.example.ExampleResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ExampleService {
    @GET("users/{user}/repos")
    suspend fun getUserRepos(@Path("user") user:String): List<ExampleResponse>

}