package com.example.eventotestedev.retrofit


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient{

        private lateinit var retrofit: Retrofit

        private val baseUrl = "http://5f5a8f24d44d640016169133.mockapi.io/api/"

        private fun getRetrofitInstance(): Retrofit{

            val httpClient = OkHttpClient.Builder()
            if(!RetrofitClient::retrofit.isInitialized){
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun <T> criarServico(classeServico: Class<T>) : T{
            return getRetrofitInstance().create(classeServico)
        }

    }

