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
import kotlinx.android.synthetic.main.activity_gestionar_perro.*
import java.lang.Exception

class GestionarPerroActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337/perro"

    private var listaPersonas: ArrayList<Persona>
    private var listaTiendas: ArrayList<TiendaMascotas>
    private var listaRazas: ArrayList<RazaPerro>

    init {
        listaTiendas = arrayListOf<TiendaMascotas>()
        listaPersonas = arrayListOf<Persona>()
        listaRazas = arrayListOf<RazaPerro>()

        getListaTiendas()
        getListaPersonas()
        getListaRazas()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_perro)
        getPerros()

    }

    fun iniciarRecyclerView(listaPerros: ArrayList<Perro>,
                            actividad: GestionarPerroActivity,
                            recyclerView: androidx.recyclerview.widget.RecyclerView/*,
                            listaPersonas: ArrayList<Persona>,
                            listaTiendas: ArrayList<TiendaMascotas>,
                            listaRazas: ArrayList<RazaPerro>*/) {
        val adaptadorPerro = AdaptadorPerro(listaPerros, actividad, recyclerView)
        rv_perros.adapter = adaptadorPerro
        rv_perros.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_perros.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)

        adaptadorPerro.notifyDataSetChanged()
    }

    fun getPerros() {
        val listaPerros: ArrayList<Perro> = arrayListOf()
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

                            val perros = Klaxon().parseArray<Perro>(data)

                            perros?.forEach { perro ->
                                listaPerros.add(perro)
                            }
                            runOnUiThread {
                                iniciarRecyclerView(listaPerros, this, rv_perros)
                            }
                        }
                    }
                }
        } catch (ex: Exception) {
            Log.i("http", "Error: ${ex}")
        }
    }

    fun getListaPersonas() {
        try {
            val url = "http://192.168.1.2:1337/persona"
            Log.i("http", url)
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

                            val personas = Klaxon()
                                .parseArray<Persona>(data)

                            personas?.forEach { persona ->
                                (
                                        this.listaPersonas.add(persona)
                                        )
                            }
                        }
                    }
                }
        } catch (ex: Exception) {
            Log.i("http", "Error: ${ex.message}")
        }
    }

    fun getListaRazas() {
        try {
            val url = "http://192.168.1.2:1337/razaPerro"
            Log.i("http", url)
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

                            val razas = Klaxon()
                                .parseArray<RazaPerro>(data)

                            razas?.forEach { raza ->
                                (
                                        this.listaRazas.add(raza)
                                        )
                            }
                        }
                    }
                }
        } catch (ex: Exception) {
            Log.i("http", "Error: ${ex.message}")
        }
    }

    fun getListaTiendas() {
        try {
            val url = "http://192.168.1.2:1337/tiendaMascotas"
            Log.i("http", url)
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

                            val tiendas = Klaxon()
                                .parseArray<TiendaMascotas>(data)

                            tiendas?.forEach { tienda ->
                                (
                                        this.listaTiendas.add(tienda)
                                        )
                            }
                        }
                    }
                }
        } catch (ex: Exception) {
            Log.i("http", "Error: ${ex.message}")
        }
    }


    fun eliminarPerro(idPerro: Int) {
        val url = url+"/?id=${idPerro}"
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
                        irAGestionarPerros()
                        Toast.makeText(this, "Perro eliminado", Toast.LENGTH_SHORT).show()


                    }
                }
            }
    }

    fun irAGestionarPerros() {
        intent = Intent(
            this,
            GestionarPerroActivity::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun irAActulizarPerro(perro: Perro) {
        intent = Intent(
            this,
            ActualizarPersonaActivity::class.java
        )
        intent.putExtra("id", perro.id as Int)
        intent.putExtra("sexo", perro.sexo)
        intent.putExtra("edad", perro.edad)
        intent.putExtra("color", perro.color)
        intent.putExtra("idRaza", perro.idRaza as Int)
        intent.putExtra("idPersona", perro.idPersona as Int)
        intent.putExtra("idTienda", perro.idTienda as Int)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
