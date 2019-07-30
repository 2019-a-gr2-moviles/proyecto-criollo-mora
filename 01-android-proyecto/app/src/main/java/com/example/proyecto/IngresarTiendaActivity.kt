package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_ingresar_tienda.*

class IngresarTiendaActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337/tiendaMascotas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_tienda)
        btn_registrar_tienda.setOnClickListener {
            registrarTienda()
        }
    }

    fun registrarTienda() {

        val tienda = TiendaMascotas(
            null,
            txt_nombre_tienda_reg.text.toString(),
            txt_direccion_tienda_reg.text.toString(),
            txt_telefono_tienda_reg.text.toString(),
            txt_latitud_reg.text.toString(),
            txt_longitud_reg.text.toString())

        val parametro=listOf(
            "nombre" to tienda.nombre,
            "direccion" to tienda.direccion,
            "telefono" to tienda.telefono,
            "latitud" to tienda.latitud,
            "longitud" to tienda.longitud)

        url.httpPost(parametro).responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    Log.i("http", "Error: ${ex.message}")
                    Toast.makeText(this, "Error al registrar tienda", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    irAGestionarTiendas()
                    Toast.makeText(this, "Tienda de mascotas registrada", Toast.LENGTH_SHORT).show()
                }
            }
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

