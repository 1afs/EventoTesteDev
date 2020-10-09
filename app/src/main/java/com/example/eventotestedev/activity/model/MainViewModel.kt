package com.example.eventotestedev.activity.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventotestedev.retrofit.DataService
import com.example.eventotestedev.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {

    private val mEventos = MutableLiveData<List<DataModel>>()
    val eventos = mEventos as LiveData<List<DataModel>>


    init {
        val conexao = RetrofitClient.criarServico(DataService::class.java)

        //Corrotina para puxar dados
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val info= withContext(Dispatchers.IO) {
                    conexao.data()
                }

                mEventos.postValue(info)

            }catch (e : Exception){
                println(e.message)
            }

        }

    }

}