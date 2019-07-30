package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_ingresar_perro.*

class IngresarPerroActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_ingresar_perro)
        //val idRaza: Int = this.intent.getIntExtra("idRaza", -1)
        btn_registrar_perro.setOnClickListener {
            try {
                val raza = listaRazas.find { raza ->
                    raza.nombreRaza == txt_raza_perro_reg.text.toString()
                }

                val persona = listaPersonas.find { persona ->
                    persona.cedula == txt_cedula_persona_reg.text.toString()
                }

                val tienda = listaTiendas.find { tienda ->
                    tienda.id == txt_id_tienda_mascota_reg.text.toString().toInt()
                }
                val perro = Perro(
                    null,
                    txt_sexo_reg.text.toString(),
                    txt_edad_perro_reg.text.toString(),
                    txt_color_reg.text.toString(),
                    raza!!.id,
                    persona!!.id,
                    tienda!!.id
                    //RazaPerro(raza!!.id, null),
                    //Persona(persona!!.id, null, null, null, null),
                    //(tienda!!.id, null, null, null, null, null)

                )
                registrarPerro(perro)


            } catch (ex: Exception) {
                Toast.makeText(this, "Error al registrar perro", Toast.LENGTH_SHORT).show()
            }
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


    fun registrarPerro(perro: Perro) {

        val parametro = listOf(
            "sexo" to perro.sexo,
            "edad" to perro.edad,
            "color" to perro.color,
            "idRaza" to perro.idRaza,
            "idPersona" to perro.idPersona,
            "idTienda" to perro.idTienda
        )

        url.httpPost(parametro).responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    Log.i("http", "Error: ${ex.message}")
                    Toast.makeText(this, "Error al registrar perro", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    irAGestionarPerros()
                    Toast.makeText(this, "Perro registrado", Toast.LENGTH_SHORT).show()

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

}
