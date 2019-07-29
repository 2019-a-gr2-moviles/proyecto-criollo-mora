package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_ingresar_raza_perro.*

class IngresarRazaPerroActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337/razaPerro"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_persona)
        btn_registrar_raza.setOnClickListener {
            registrarRaza()
        }
    }



    fun registrarRaza() {

        val raza = RazaPerro(
            null,
            txt_nombre_raza_reg.text.toString()
            //null
           )

        val parametro=listOf(
            "nombreRaza" to raza.nombreRaza
            )

        url.httpPost(parametro).responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    Log.i("http", "Error: ${ex.message}")
                    //Toast.makeText(this, "Error:${ex}", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    irAGestionarRazas()
                    Toast.makeText(this, "Raza de perro registrada", Toast.LENGTH_SHORT).show()


                }
            }
        }
    }

    fun irAGestionarRazas() {
        intent = Intent(
            this,
            GestionarRazaPerroActivity::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
