package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_opciones_tienda_mascotas.*

class OpcionesTiendaMascotasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones_tienda_mascotas)
        btn_ingresar_tienda.setOnClickListener {
            irAIngresarTienda()
        }

        btn_gestionar_tienda.setOnClickListener {
            irAGestionarTienda()
        }

        btn_mapa.setOnClickListener {
            irAMapa()
        }
    }

    fun irAIngresarTienda(){
        val intentExplicito = Intent(
            this,
            IngresarTiendaActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irAGestionarTienda(){
        val intentExplicito = Intent(
            this,
            GestionarTiendaActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irAMapa(){
        val intentExplicito = Intent(
            this,
            MapsActivity::class.java
        )
        intentExplicito.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intentExplicito)
    }

}
