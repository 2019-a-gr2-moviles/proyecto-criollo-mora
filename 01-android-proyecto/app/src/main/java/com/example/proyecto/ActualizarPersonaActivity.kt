package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_actualizar_persona.*

class ActualizarPersonaActivity : AppCompatActivity() {

    var url = "http://192.168.1.2:1337/persona"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_persona)
        val nombre: String = this.intent.getStringExtra("nombre")
        val apellido: String = this.intent.getStringExtra("apellido")
        val cedula: String = this.intent.getStringExtra("cedula")
        val fechaNac: String = this.intent.getStringExtra("fechaNac")
        val id: Int? = this.intent.getIntExtra("id", -1)

        txt_nombre_persona_mod.hint = nombre
        txt_apellido_mod.hint = apellido
        txt_fecha_nac_mod.hint = fechaNac
        txt_cedula_mod.hint = cedula
        txt_id_persona_mod.text = id.toString()

        btn_modificar_persona.setOnClickListener {
            try {
                val persona =
                    Persona(
                        txt_id_persona_mod.text.toString().toInt(),
                        txt_nombre_persona_mod.text.toString(),
                        txt_apellido_mod.text.toString(),
                        txt_cedula_mod.text.toString(),
                        txt_fecha_nac_mod.text.toString()
                    )
                actualizarPersona(persona)
            }
                catch (ex: Exception) {
                    Log.i("http", "Error: ${ex.message}")
                    //Toast.makeText(this, "Error:${ex}", Toast.LENGTH_SHORT).show()
                }

        }

    }

    fun actualizarPersona(persona: Persona) {
        val url = url + "/${persona.id}"
        Log.i("actualizar", "url: ${url}")

        try {

        val json = """
            {
            "nombre": "${persona.nombre}",
            "apellido": "${persona.apellido}",
            "cedula": "${persona.cedula}",
            "fechaNac": "${persona.fechaNac}"
            }"""

        url.httpPut().body(json)
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.i("http", "Error: ${ex.message}")
                    }
                    is Result.Success -> {
                        irAGestionarPersonas()
                        Toast.makeText(this, "Persona modificada", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        catch (ex: Exception) {
            Log.i("http", "Error: ${ex.message}")
            //Toast.makeText(this, "Error:${ex}", Toast.LENGTH_SHORT).show()
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
