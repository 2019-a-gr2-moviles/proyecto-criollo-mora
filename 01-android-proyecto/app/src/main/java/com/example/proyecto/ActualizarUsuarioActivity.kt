package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_actualizar_usuario.*

class ActualizarUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_usuario)
        val username: String? = this.intent.getStringExtra("username")
        val password: String? = this.intent.getStringExtra("password")
        val tipo: String? = this.intent.getStringExtra("tipo")
        val id: Int? = this.intent.getIntExtra("id", -1)

        txt_username_mod.hint = username
        txt_password_mod.hint = password
        txt_tipo_mod.hint = tipo
        txt_id_usuario_mod.text = id.toString()

        btn_modificar_usuario.setOnClickListener {
            val usuario =
                Usuario(
                    txt_id_usuario_mod.text.toString().toInt(),
                    txt_username_mod.text.toString(),
                    txt_password_mod.text.toString(),
                    txt_tipo_mod.text.toString()
                )
            actualizarUsuario(usuario)

        }

    }

    fun actualizarUsuario(usuario: Usuario) {
        val url = "http://192.168.1.2:1337/usuario" + "/${usuario.id}"
        val json = """
            {
            "username": "${usuario.username}",
            "password": "${usuario.password}",
            "tipo": "${usuario.tipo}"
            }"""

        url.httpPut().body(json)
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.i("http", "Error: ${ex.message}")
                    }
                    is Result.Success -> {
                        Toast.makeText(this, "Usuario modificado", Toast.LENGTH_SHORT).show()
                        runOnUiThread {
                            irAGestionarUsuarios()
                        }
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
