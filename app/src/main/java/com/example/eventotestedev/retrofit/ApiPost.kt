package com.example.eventotestedev.retrofit


import android.util.Log
import com.example.eventotestedev.activity.model.DadosPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiPost {
     fun addCheckin(dadosUsuario : DadosPost, onResult : (DadosPost?) -> Unit){
        val retrofit = RetrofitClient.criarServico(PostService::class.java)
        retrofit.post(dadosUsuario).enqueue(
            object : Callback<DadosPost> {
                override fun onFailure(call: Call<DadosPost>, t: Throwable) {
                    Log.e("error", t.message.toString())
                }
                override fun onResponse(call: Call<DadosPost>, response: Response<DadosPost>) {
                    val feedback = response.body()
                    onResult(feedback!!)
                }
            }
        )
    }
}

