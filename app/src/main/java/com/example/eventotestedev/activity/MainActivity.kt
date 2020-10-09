package com.example.eventotestedev.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventotestedev.activity.model.DataModel
import com.example.eventotestedev.activity.model.MainViewModel
import com.example.eventotestedev.R
import com.example.eventotestedev.activity.adapter.Adapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var viewAdapter: Adapter
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //Configurando adaptador
        viewAdapter = Adapter(::onClick)

        // Configurando RecyclerView
        viewManager = LinearLayoutManager(this)

        recyclerEvents.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewModel.eventos.observe(this, { itemList ->
            viewAdapter.submitList(itemList)
        })

    }
    // Adicionar evento de click
     fun onClick(dataModel : DataModel) {

         val intent = Intent (this, Detalhes::class.java).apply {
             putExtra("dados", dataModel)
         }
         startActivity(intent)
    }
}