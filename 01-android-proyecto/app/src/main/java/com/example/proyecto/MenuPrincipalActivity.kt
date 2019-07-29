package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_principal.*

class MenuPrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        btn_usuarios.setOnClickListener {
            irAUsuario()
        }

        btn_personas.setOnClickListener {
            irAPersona()
        }

        btn_razas.setOnClickListener {
            irARaza()
        }

        btn_perros.setOnClickListener {
            irAPerros()
        }

        btn_tiendas.setOnClickListener {
            irATiendas()
        }
    }

    fun irAUsuario(){
        val intentExplicito = Intent(
            this,
            OpcionesUsuariosActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irAPersona(){
        val intentExplicito = Intent(
            this,
            OpcionesPersonaActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irARaza(){
        val intentExplicito = Intent(
            this,
            OpcionesRazaPerroActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irAPerros(){
        val intentExplicito = Intent(
            this,
            OpcionesPerroActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irATiendas(){
        val intentExplicito = Intent(
            this,
            OpcionesTiendaMascotasActivity::class.java
        )
        startActivity(intentExplicito)
    }
}
