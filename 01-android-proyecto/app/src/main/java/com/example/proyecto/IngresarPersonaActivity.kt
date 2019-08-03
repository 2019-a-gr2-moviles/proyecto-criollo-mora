package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_ingresar_persona.*

class IngresarPersonaActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337/persona"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_persona)
        btn_registrar_persona.setOnClickListener {
            registrarPersona()
        }
    }


    fun registrarPersona() {

        val persona = Persona(
            null,
            txt_nombre_reg.text.toString(),
            txt_apellido_reg.text.toString(),
            txt_cedula_reg.text.toString(),
            txt_fecha_nac_reg.text.toString())

        val parametro=listOf(
            "cedula" to persona.cedula,
            "nombre" to persona.nombre,
            "apellido" to persona.apellido,
            "fechaNac" to persona.fechaNac)

        url.httpPost(parametro).responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    Log.i("http", "Error: ${ex.message}")
                    Toast.makeText(this, "Error al registrar persona", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    runOnUiThread {
                        Toast.makeText(this, "Persona registrada", Toast.LENGTH_SHORT).show()
                        irAGestionarPersonas()
                    }
                }
            }
        }
    }

    fun irAGestionarPersonas() {
        intent = Intent(
            this,
            GestionarPersonaActivity::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
