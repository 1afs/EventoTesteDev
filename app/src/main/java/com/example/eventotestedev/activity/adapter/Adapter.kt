package com.example.eventotestedev.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eventotestedev.activity.model.DataModel
import com.example.eventotestedev.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


// Adaptador para a entrada de dados
class Adapter(val action : (DataModel) -> Unit ) : ListAdapter<DataModel, Adapter.ViewHolder>(
    object : DiffUtil.ItemCallback<DataModel>() {
        override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem.titulo == newItem.titulo
        }

        override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem == newItem
        }
    }
) {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titulo : TextView = itemView.findViewById(R.id.idAdapterNome)
        val data : TextView = itemView.findViewById(R.id.idAdapterData)
        val preco : TextView = itemView.findViewById(R.id.idAdapterPrice)
        val imagem : ImageView = itemView.findViewById(R.id.idImagemEvento)
        val pessoas : TextView = itemView.findViewById(R.id.idAdapterPessoas)
    }


    //Criar as visualizações
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLista : View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_events, parent, false)
        return ViewHolder(itemLista)
    }

    //Exibe os itens
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val event = getItem(position)
        Picasso.get().load(event.imagem).into(holder.imagem)

        holder.data.text = converterLongToDate(event.data.toLong())
        holder.titulo.text = event.titulo
        val valor = event.valor.toBigDecimal()
        holder.preco.text = "R$ $valor"
        holder.pessoas.text = event.pessoas.size.toString() + " pessoas confirmadas"
        //holder.pessoas.setText("event.pessoas.size ")
        holder.itemView.setOnClickListener { action(event) }
    }

    //Quantidade de itens a serem exibidos
    override fun getItemCount(): Int = currentList.size

    private fun converterLongToDate(long : Long): String {
        val date = Date(long)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }

}