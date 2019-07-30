package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_gestionar_tienda.*
import java.lang.Exception

class GestionarTiendaActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337/tiendaMascotas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_tienda)
        getTiendas()
    }

    fun iniciarRecyclerView(listaTiendas: ArrayList<TiendaMascotas>, actividad: GestionarTiendaActivity, recyclerView: androidx.recyclerview.widget.RecyclerView) {
        val adaptadorTienda = AdaptadorTienda(listaTiendas, actividad, recyclerView)
        rv_tiendas.adapter = adaptadorTienda
        rv_tiendas.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_tiendas.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)

        adaptadorTienda.notifyDataSetChanged()
    }

    fun getTiendas() {
        val listaTiendas: ArrayList<TiendaMascotas> = arrayListOf()
        try {
            //val url = "http://192.168.1.2:1337/persona"
            url.httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http", "Error: ${ex.message}")
                        }
                        is Result.Success -> {
                            val data = result.get()
                            Log.i("http", "Data: ${data}")

                            val tiendas = Klaxon().parseArray<TiendaMascotas>(data)

                            tiendas?.forEach { tienda ->
                                listaTiendas.add(tienda)
                            }
                            runOnUiThread {
                                iniciarRecyclerView(listaTiendas, this, rv_tiendas)
                            }
                        }
                    }
                }
        } catch (ex: Exception) {
            Log.i("http", "Error: ${ex}")
        }
    }

    fun eliminarTienda(idTienda: Int) {
        val url = url+"/?id=${idTienda}"
        Log.i("eliminar", "url: ${url}")

        url.httpDelete()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        //Toast.makeText(this, "Error:${ex}", Toast.LENGTH_SHORT).show()
                        Log.i("http", "Error: ${ex.message}")
                    }
                    is Result.Success -> {
                        irAGestionarTiendas()
                        Toast.makeText(this, "Tienda eliminada", Toast.LENGTH_SHORT).show()


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

    fun irAActulizarTienda(tiendaMascotas: TiendaMascotas) {
        intent = Intent(
            this,
            ActualizarPersonaActivity::class.java
        )
        intent.putExtra("id", tiendaMascotas.id as Int)
        intent.putExtra("nombre", tiendaMascotas.nombre)
        intent.putExtra("direccion", tiendaMascotas.direccion)
        intent.putExtra("telefono", tiendaMascotas.telefono)
        intent.putExtra("latitud", tiendaMascotas.latitud)
        intent.putExtra("longitud", tiendaMascotas.longitud)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
