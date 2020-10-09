package com.example.eventotestedev.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventotestedev.R
import com.example.eventotestedev.activity.model.DadosPost
import com.example.eventotestedev.activity.model.DataModel
import com.example.eventotestedev.retrofit.ApiPost
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhes.*


class Detalhes : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var dialog : Dialog
    private lateinit var email : String
    private lateinit var nome : String
    private var latitude : Double = 0.0
    private var longitude : Double = 0.0
    private var evento : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        //Recebe dados da activity anterior
        val bundle: Bundle = intent.extras!!
        val dados: DataModel = bundle.getParcelable("dados")!!

        //preenche as variáveis com os resultados
        Picasso.get().load(dados.imagem).into(idDetalheImagem)
        idDetalhesDescricao.text = dados.descricao
        idDetalhesPreco.text = "R$ " + dados.valor
        idDetalhesEvento.text = dados.titulo
        evento = dados.titulo
        latitude = dados.latitude.toDouble()
        longitude = dados.longitude.toDouble()

        //Preparação do campo do Mapa
        val mapFragment : SupportMapFragment = (supportFragmentManager
            .findFragmentById(R.id.idMap) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)

        //Botão de compartilhar
        floatingButtonShare.setOnClickListener {
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Olá, Gostaria de participar do evento '${dados.titulo}' ? "
            )
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent, "Compartilhar com: "))
        }

        floatingButtonCheckin.setOnClickListener{
            showDialog(dados.id)
        }

    }

    //Exibir mapa com os dados recebidos anteriormente
        override fun onMapReady(googleMap: GoogleMap) {
            val local = LatLng(latitude,longitude)
            googleMap.addMarker(
                MarkerOptions().position(local)
                    .title(evento)
            )
            val zoom = 16.0f
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local,zoom))
        }
    
// Dialog para preencher os dados para check-in
    private fun showDialog(idEvento: Short){
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_box)

        val botao = dialog.findViewById<Button>(R.id.buttonCheckin)
        val editNome = dialog.findViewById<EditText>(R.id.idCheckinNome)
        val editEmail = dialog.findViewById<EditText>(R.id.idCheckinEmail)


        botao.setOnClickListener {
            if(editNome.text.toString().isEmpty()|| editEmail.text.toString().isEmpty()){
                Toast.makeText(this, "Verifique se não há campos em branco", Toast.LENGTH_LONG).show()
            }else {

                    nome = editNome.text.toString()
                    email = editEmail.text.toString()
                    val apiservice = ApiPost()
                    val infoCheckin = DadosPost(idEvento, nome, email)

                //Preparar dados para enviar por post
                    apiservice.addCheckin(infoCheckin) {
                        if (it?.eventId != null) {
                            Toast.makeText(
                                this@Detalhes,
                                "Check-in realizado com sucesso",
                                Toast.LENGTH_LONG
                            ).show()
                            dialog.cancel()

                        } else {
                            Toast.makeText(
                                this@Detalhes,
                                "Ocorreu um erro",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
        dialog.show()

    }

}