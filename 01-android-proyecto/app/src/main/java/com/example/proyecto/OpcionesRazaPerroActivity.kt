package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_opciones_raza_perro.*

class OpcionesRazaPerroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones_raza_perro)
        btn_ingresar_raza.setOnClickListener {
            irAIngresarRaza()
        }

        btn_gestionar_raza.setOnClickListener {
            irAGestionarRaza()
        }
    }

    fun irAIngresarRaza(){
        val intentExplicito = Intent(
            this,
            IngresarRazaPerroActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irAGestionarRaza(){
        val intentExplicito = Intent(
            this,
            GestionarRazaPerroActivity::class.java
        )
        startActivity(intentExplicito)
    }

}
