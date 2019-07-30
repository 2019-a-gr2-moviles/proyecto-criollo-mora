package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_ingresar_usuario.*

class IngresarUsuarioActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337/usuario"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_usuario)
        btn_registrar_usuario.setOnClickListener {
            registrarUsuario()
        }
    }


    fun registrarUsuario() {
        val usuario = Usuario(null ,
            txt_username_reg.text.toString(),
            txt_password_reg.text.toString())

        val parametro=listOf(
            "username" to usuario.username,
            "password" to usuario.password)
        url.httpPost(parametro).responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    Log.i("http", "Error: ${ex.message}")
                    Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
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
