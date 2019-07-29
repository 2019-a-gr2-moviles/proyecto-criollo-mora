package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_opciones_perro.*

class OpcionesPerroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones_perro)
        btn_ingresar_perro.setOnClickListener {
            irAIngresarPerro()
        }

        btn_gestionar_perro.setOnClickListener {
            irAGestionarPerro()
        }
    }

    fun irAIngresarPerro(){
        val intentExplicito = Intent(
            this,
            IngresarPerroActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irAGestionarPerro(){
        val intentExplicito = Intent(
            this,
            GestionarPerroActivity::class.java
        )
        startActivity(intentExplicito)
    }

}
