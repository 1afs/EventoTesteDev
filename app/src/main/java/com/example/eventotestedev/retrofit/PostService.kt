package com.example.eventotestedev.retrofit

import com.example.eventotestedev.activity.model.DadosPost
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostService {
    @Headers("Content-Type: application/json")
    @POST("checkin")
     fun post(@Body dataUser : DadosPost) : Call<DadosPost>
}