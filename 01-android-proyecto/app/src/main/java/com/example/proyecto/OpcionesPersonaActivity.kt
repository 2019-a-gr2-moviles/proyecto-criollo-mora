package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_opciones_persona.*

class OpcionesPersonaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones_persona)
        btn_ingresar_persona.setOnClickListener {
            irAIngresarPersona()
        }

        btn_gestionar_persona.setOnClickListener {
            irAGestionarPersona()
        }
    }

    fun irAIngresarPersona(){
        val intentExplicito = Intent(
            this,
            IngresarPersonaActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irAGestionarPersona(){
        val intentExplicito = Intent(
            this,
            GestionarPersonaActivity::class.java
        )
        startActivity(intentExplicito)
    }

}
