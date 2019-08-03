package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_actualizar_perro.*
import kotlinx.android.synthetic.main.activity_actualizar_raza_perro.*

class ActualizarPerroActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_actualizar_perro)
        val id: Int? = this.intent.getIntExtra("id", -1)
        val sexo: String = this.intent.getStringExtra("sexo")
        val edad: String = this.intent.getStringExtra("edad")
        val color: String = this.intent.getStringExtra("color")
        val idRaza: String? = this.intent.getStringExtra("idRaza")
        val idPersona: String? = this.intent.getStringExtra("idPersona")
        val idTienda: Int? = this.intent.getIntExtra("idTienda", -1)

        txt_sexo_perro_mod.hint = sexo
        txt_edad_perro_mod.hint = edad
        txt_color_perro_mod.hint = color
        //txt_id_tienda_perro_mod.hint = idTienda
        //txt_id_raza_mod.text = id.toString()
        txt_raza_perro_mod.hint = idRaza
        txt_cedula_persona_mod.hint = idPersona
        txt_id_tienda_perro_mod.hint = idTienda.toString()

        btn_modificar_perro.setOnClickListener {
            try {
                val razaPerro = listaRazas.find { razaPerro ->
                    razaPerro.nombreRaza == txt_raza_perro_mod.text.toString()
                }

                val persona = listaPersonas.find { persona ->
                    persona.cedula == txt_cedula_persona_mod.text.toString()
                }

                val tiendaMascotas = listaTiendas.find { tiendaMascotas ->
                    tiendaMascotas.id == txt_id_tienda_perro_mod.text.toString().toInt()
                }
                val perro =
                    Perro(
                        id,
                        txt_sexo_perro_mod.text.toString(),
                        txt_edad_perro_mod.text.toString(),
                        txt_color_perro_mod.text.toString(),
                        /*razaPerro!!.id,
                        persona!!.id,
                        tiendaMascotas!!.id*/
                        PerrosXRaza(razaPerro!!.id, null),
                        PerrosXPersona(persona!!.id, null, null, null, null),
                        PerrosXTiendaMascotas(txt_id_tienda_perro_mod.text.toString().toInt(), null, null, null, null, null)

                    )
                actualizarPerro(perro)
            }
            catch (ex: Exception) {
                Log.i("http", "Error: ${ex.message}")
                //Toast.makeText(this, "Error:${ex}", Toast.LENGTH_SHORT).show()
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


    fun actualizarPerro(perro : Perro) {
        val url = url + "/${perro.id}"
        Log.i("actualizar", "url: ${url}")

        try {

            val json = """
            {
            "sexo": "${perro.sexo}",
            "edad": "${perro.edad}",
            "color": "${perro.color}",
            "idRaza": "${perro.idRaza!!.id}",
            "idPersona": "${perro.idPersona!!.id}",
            "idTienda": "${perro.idTienda!!.id}"

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
                                Toast.makeText(this, "Perro modificado", Toast.LENGTH_SHORT).show()
                                irAGestionarPerros()
                            }

                        }
                    }
                }
        }
        catch (ex: Exception) {
            Log.i("http", "Error: ${ex.message}")
            Toast.makeText(this, "Error al modificar perro", Toast.LENGTH_SHORT).show()
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
