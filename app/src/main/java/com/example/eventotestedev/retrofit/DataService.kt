package com.example.eventotestedev.retrofit

import com.example.eventotestedev.activity.model.DataModel
import retrofit2.http.GET

interface DataService {

    @GET("events")
    suspend fun data() : MutableList<DataModel>

}