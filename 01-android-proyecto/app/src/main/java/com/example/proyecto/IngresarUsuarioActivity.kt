package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_ingresar_usuario.*

class IngresarUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_usuario)
        btn_registrar.setOnClickListener {
            registrarUsuario()
        }
    }



    fun registrarUsuario() {
        val url = "http://192.168.1.3:1337/usuario"
        val usuario = Usuario(id = null ,
            username = txt_username.text.toString(),
            password = txt_password.text.toString(),
            tipo = txt_tipo.text.toString())
        val parametro=listOf(
            "username" to usuario.username,
            "password" to usuario.password,
            "tipo" to usuario.tipo)
        url.httpPost(parametro).responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val exc = result.getException()
                    Toast.makeText(this, "Error:${exc}", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

}
