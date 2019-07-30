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
import kotlinx.android.synthetic.main.activity_gestionar_usuario.*
import java.lang.Exception

class GestionarUsuarioActivity : AppCompatActivity() {
    var url = "http://192.168.1.2:1337/usuario"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_usuario)
        getUsuarios()

    }

    fun iniciarRecyclerView(listaUsuarios: ArrayList<Usuario>, actividad: GestionarUsuarioActivity, recyclerView: androidx.recyclerview.widget.RecyclerView) {
        val adaptadorUsuario = AdaptadorUsuario(listaUsuarios, actividad, recyclerView)
        rv_usuarios.adapter = adaptadorUsuario
        rv_usuarios.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_usuarios.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)

        adaptadorUsuario.notifyDataSetChanged()
    }

    fun getUsuarios() {
        val listaUsuarios: ArrayList<Usuario> = arrayListOf()
        try {
            //val url = "http://192.168.1.2:1337/usuario"
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

                            val usuarios = Klaxon().parseArray<Usuario>(data)

                            usuarios?.forEach { usuario ->
                                listaUsuarios.add(usuario)
                            }
                            runOnUiThread {
                                iniciarRecyclerView(listaUsuarios, this, rv_usuarios)
                            }
                        }
                    }
                }
        } catch (ex: Exception) {
            Log.i("http", "Error: ${ex}")
        }
    }

    fun eliminarUsuario(idUsuario: Int) {
        val url = url+ "/?id=${idUsuario}"
        url.httpDelete()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        Toast.makeText(this, "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
                        Log.i("http", "Error: ${ex.message}")
                    }
                    is Result.Success -> {
                        irAGestionarUsuarios()
                        Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show()


                    }
                }
            }
    }

    fun irAGestionarUsuarios() {
        intent = Intent(
            this,
            GestionarUsuarioActivity::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun irAActulizarUsuario(usuario: Usuario) {
        intent = Intent(
            this,
            ActualizarUsuarioActivity::class.java
        )
        intent.putExtra("id", usuario.id as Int)
        intent.putExtra("username", usuario.username)
        intent.putExtra("password", usuario.password)


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
