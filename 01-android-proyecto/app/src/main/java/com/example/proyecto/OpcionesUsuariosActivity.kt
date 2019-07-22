package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_opciones_usuarios.*

class OpcionesUsuariosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones_usuarios)

        btn_ingresar.setOnClickListener {
            irAIngresarUsuario()
        }

        btn_gestionar.setOnClickListener {
            irAGestionarUsuario()
        }
    }

    fun irAIngresarUsuario(){
        val intentExplicito = Intent(
            this,
            IngresarUsuarioActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irAGestionarUsuario(){
        val intentExplicito = Intent(
            this,
            GestionarUsuarioActivity::class.java
        )
        startActivity(intentExplicito)
    }


}
