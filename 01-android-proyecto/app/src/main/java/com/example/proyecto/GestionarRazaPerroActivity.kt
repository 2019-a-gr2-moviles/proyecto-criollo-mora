package com.example.proyecto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_gestionar_raza_perro.*
import java.lang.Exception

class GestionarRazaPerroActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337/razaPerro"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_raza_perro)
        getRazas()

    }

    fun iniciarRecyclerView(listaRazas: ArrayList<RazaPerro>, actividad: GestionarRazaPerroActivity, recyclerView: RecyclerView) {
        val adaptadorRazaPerro = AdaptadorRazaPerro(listaRazas, actividad, recyclerView)
        rv_razas_perro.adapter = adaptadorRazaPerro
        rv_razas_perro.itemAnimator = DefaultItemAnimator()
        rv_razas_perro.layoutManager = LinearLayoutManager(actividad)

        adaptadorRazaPerro.notifyDataSetChanged()
    }

    fun getRazas() {
        val listaRazas: ArrayList<RazaPerro> = arrayListOf()
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

                            val razas = Klaxon().parseArray<RazaPerro>(data)

                            razas?.forEach { raza ->
                                listaRazas.add(raza)
                            }
                            runOnUiThread {
                                iniciarRecyclerView(listaRazas, this, rv_razas_perro)
                            }
                        }
                    }
                }
        } catch (ex: Exception) {
            Log.i("http", "Error: ${ex}")
        }
    }

    fun eliminarRaza(idRaza: Int) {
        val url = url+"/?id=${idRaza}"
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
                        irAGestionarRazas()
                        Toast.makeText(this, "Raza de perro eliminada", Toast.LENGTH_SHORT).show()


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

    fun irAActulizarRaza(razaPerro: RazaPerro) {
        intent = Intent(
            this,
            ActualizarRazaPerroActivity::class.java
        )
        intent.putExtra("id", razaPerro.id as Int)
        intent.putExtra("nombreRaza", razaPerro.nombreRaza)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
