package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_ingresar_usuario.setOnClickListener {
            consultarIngreso()
        }
    }




    fun consultarIngreso(){
        val lstUsuario = ArrayList<Usuario>()
        val urlUsuario = url+"/usuario"
        val usuario = Usuario(null,
            txt_username_ly.text.toString(),
            txt_password_reg.text.toString())

        val parametro=listOf("username" to usuario.username, "password" to usuario.password)
        urlUsuario.httpGet(parametro).responseString { request, response, result ->
            when(result){
                is Result.Failure ->{
                    val ex = result.getException()
                    Toast.makeText(this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    val resultado = result.get()
                    val jsonResultado= Klaxon().parseArray<Usuario>(resultado)
                    if (jsonResultado!= null) {
                        var intent = Intent(this, MenuPrincipalActivity::class.java)
                        startActivity(intent)
                    }

                }

            }
        }
    }
}
