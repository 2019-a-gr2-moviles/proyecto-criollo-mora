package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_ingresar_usuario.*

class IngresarUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_usuario)
        btn_registrar_usuario.setOnClickListener {
            registrarUsuario()
        }
    }



    fun registrarUsuario() {
        val url = "http://192.168.1.2:1337/usuario"
        val usuario = Usuario(id = null ,
            username = txt_username_reg.text.toString(),
            password = txt_password_reg.text.toString(),
            tipo = txt_tipo_usuario_reg.text.toString())
        val parametro=listOf(
            "username" to usuario.username,
            "password" to usuario.password,
            "tipo" to usuario.tipo)
        url.httpPost(parametro).responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    Log.i("http", "Error: ${ex.message}")
                    Toast.makeText(this, "Error:${ex}", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    irAGestionarUsuarios()
                    Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()

                }

            }
        }
    }

    fun irAGestionarUsuarios() {
        intent = Intent(
            this,
            GestionarUsuarioActivity::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
