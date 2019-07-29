package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_actualizar_tienda.*

class ActualizarTiendaActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337/tiendaMascotas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_tienda)
        val nombre: String = this.intent.getStringExtra("nombre")
        val direccion: String = this.intent.getStringExtra("direccion")
        val telefono: String = this.intent.getStringExtra("telefono")
        val latitud: String = this.intent.getStringExtra("latitud")
        val longitud: String = this.intent.getStringExtra("longitud")
        val id: Int? = this.intent.getIntExtra("id", -1)

        txt_nombre_tienda_mod.hint = nombre
        txt_direccion_tienda_mod.hint = direccion
        txt_telefono_tienda_mod.hint = telefono
        txt_latitud_mod.hint = latitud
        txt_longitud_mod.hint = longitud
        txt_id_tienda_mod.text = id.toString()

        btn_modificar_tienda.setOnClickListener {
            try {
                val tiendaMascotas =
                    TiendaMascotas(
                        txt_id_tienda_mod.text.toString().toInt(),
                        txt_nombre_tienda_mod.text.toString(),
                        txt_direccion_tienda_mod.text.toString(),
                        txt_telefono_tienda_mod.text.toString(),
                        txt_latitud_mod.text.toString(),
                        txt_longitud_mod.text.toString()
                    )
                actualizarTienda(tiendaMascotas)
            }
            catch (ex: Exception) {
                Log.i("http", "Error: ${ex.message}")
                Toast.makeText(this, "Error al modificar tienda", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun actualizarTienda(tiendaMascotas: TiendaMascotas) {
        val url = url + "/${tiendaMascotas.id}"
        Log.i("actualizar", "url: ${url}")

        try {

            val json = """
            {
            "nombre": "${tiendaMascotas.nombre}",
            "direccion": "${tiendaMascotas.direccion}",
            "telefono": "${tiendaMascotas.telefono}",
            "latitud": "${tiendaMascotas.latitud}"
            "longitud": "${tiendaMascotas.longitud}"
            }"""

            url.httpPut().body(json)
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http", "Error: ${ex.message}")
                        }
                        is Result.Success -> {
                            irAGestionarTiendas()
                            Toast.makeText(this, "Tienda de mascotas modificada", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
        catch (ex: Exception) {
            Log.i("http", "Error: ${ex.message}")
            Toast.makeText(this, "Error al modificar tienda", Toast.LENGTH_SHORT).show()
        }

    }

    fun irAGestionarTiendas() {
        intent = Intent(
            this,
            GestionarTiendaActivity::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
