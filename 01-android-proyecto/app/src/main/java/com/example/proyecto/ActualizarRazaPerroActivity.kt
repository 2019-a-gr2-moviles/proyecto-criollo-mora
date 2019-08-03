package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_actualizar_raza_perro.*

class ActualizarRazaPerroActivity : AppCompatActivity() {

    var url = "http://192.168.1.2:1337/razaPerro"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_raza_perro)
        val nombreRaza: String = this.intent.getStringExtra("nombreRaza")
        val id: Int? = this.intent.getIntExtra("id", -1)

        txt_nombre_raza_mod.hint = nombreRaza
        txt_id_raza_mod.text = id.toString()

        btn_modificar_raza.setOnClickListener {
            try {
                val razaPerro =
                    RazaPerro(
                        txt_id_raza_mod.text.toString().toInt(),
                        txt_nombre_raza_mod.text.toString()

                    )
                actualizarRaza(razaPerro)
            }
            catch (ex: Exception) {
                Log.i("http", "Error: ${ex.message}")
                //Toast.makeText(this, "Error:${ex}", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun actualizarRaza(razaPerro: RazaPerro) {
        val url = url + "/${razaPerro.id}"
        Log.i("actualizar", "url: ${url}")

        try {

            val json = """
            {
            "nombreRaza": "${razaPerro.nombreRaza}"
            }"""

            url.httpPut().body(json)
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http", "Error: ${ex.message}")
                        }
                        is Result.Success -> {
                            runOnUiThread {
                                Toast.makeText(this, "Raza de perro modificada", Toast.LENGTH_SHORT).show()
                                irAGestionarRazas()
                            }

                        }
                    }
                }
        }
        catch (ex: Exception) {
            Log.i("http", "Error: ${ex.message}")
            Toast.makeText(this, "Error al modificar raza de perro", Toast.LENGTH_SHORT).show()
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
