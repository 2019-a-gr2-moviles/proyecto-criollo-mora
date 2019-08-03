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
import kotlinx.android.synthetic.main.activity_gestionar_persona.*
import java.lang.Exception

class GestionarPersonaActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337/persona"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPersonas()
        setContentView(R.layout.activity_gestionar_persona)


    }

    fun iniciarRecyclerView(listaPersonas: ArrayList<Persona>, actividad: GestionarPersonaActivity, recyclerView: androidx.recyclerview.widget.RecyclerView) {
        val adaptadorPersona = AdaptadorPersona(listaPersonas, actividad, recyclerView)
        rv_personas.adapter = adaptadorPersona
        rv_personas.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_personas.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)

        adaptadorPersona.notifyDataSetChanged()
    }

    fun getPersonas() {
        val listaPersonas: ArrayList<Persona> = arrayListOf()
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

                            val personas = Klaxon().parseArray<Persona>(data)

                            personas?.forEach { persona ->
                                listaPersonas.add(persona)
                            }
                            runOnUiThread {
                                iniciarRecyclerView(listaPersonas, this, rv_personas)
                            }
                        }
                    }
                }
        } catch (ex: Exception) {
            Log.i("http", "Error: ${ex}")
        }
    }

    fun eliminarPersona(idPersona: Int) {
        //val url = url+"?id=${idPersona}"
        val url = url+"/${idPersona}"
        Log.i("eliminar", "url: ${url}")
        getPersonas()

        url.httpDelete()
            .responseString { request, response, result ->
                when (result) {

                    is Result.Failure -> {
                        val ex = result.getException()
                        Toast.makeText(this, "Error al eliminar persona", Toast.LENGTH_SHORT).show()
                        Log.i("http", "Error: ${ex.message}")
                    }
                    is Result.Success -> {
                        runOnUiThread {
                            getPersonas()
                            Toast.makeText(this, "Persona eliminada", Toast.LENGTH_SHORT).show()
                            startActivity(this.intent)
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

    fun irAActulizarPersona(persona: Persona) {
        intent = Intent(
            this,
            ActualizarPersonaActivity::class.java
        )
        intent.putExtra("id", persona.id as Int)
        intent.putExtra("nombre", persona.nombre)
        intent.putExtra("apellido", persona.apellido)
        intent.putExtra("cedula", persona.cedula)
        intent.putExtra("fechaNac", persona.fechaNac)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
