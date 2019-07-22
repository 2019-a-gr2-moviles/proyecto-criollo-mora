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
    }

    fun irAUsuario(){
        val intentExplicito = Intent(
            this,
            OpcionesUsuariosActivity::class.java
        )
        startActivity(intentExplicito)
    }
}
