package com.example.proyecto

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class AdaptadorUsuario (
    private val listaUsuarios: ArrayList<Usuario>,
    private val contexto: GestionarUsuarioActivity,
    private val recyclerView: RecyclerView
) :
    RecyclerView.Adapter<AdaptadorUsuario.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var usernameTextView: TextView
        var tipoTextView: TextView
        var idTextView: TextView
        var eliminarButton: Button
        var modificarButton: Button

        init {
            usernameTextView = view.findViewById(R.id.txt_username_ly) as TextView
            tipoTextView = view.findViewById(R.id.txt_tipo_usuario_ly) as TextView
            idTextView = view.findViewById(R.id.txt_id_usuario_ly) as TextView
            eliminarButton = view.findViewById(R.id.btn_eliminar_usuario) as Button
            modificarButton = view.findViewById(R.id.btn_modificar_usuario) as Button

            val layout = view.findViewById(R.id.linear_layout_usuario) as LinearLayout
            layout.setOnClickListener {
                val usuario = crearUsuario(
                    idTextView.text.toString().toInt(),
                    usernameTextView.text.toString(),
                    tipoTextView.text.toString()
                )
                contexto.irAActulizarUsuario(usuario)

            }

            eliminarButton.setOnClickListener {
                val usuario = crearUsuario(
                    idTextView.text.toString().toInt(),
                    usernameTextView.text.toString(),
                    tipoTextView.text.toString()
                )
                contexto.eliminarUsuario(usuario)

            }

            modificarButton.setOnClickListener {
                val usuario = crearUsuario(
                    idTextView.text.toString().toInt(),
                    usernameTextView.text.toString(),
                    tipoTextView.text.toString()
                )
                contexto.irAActulizarUsuario(usuario)

            }
        }

    }

    //Esta función define el template que vamos a utilizar.
    // El template esta en la carpeta de recursos res/layout -> layout
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdaptadorUsuario.MyViewHolder {
        val itemView = LayoutInflater
            .from(p0.context)
            .inflate(
                R.layout.layout_usuario,
                p0,
                false
            )
        return MyViewHolder(itemView)
    }

    //Devuelve el # de items de la lista
    override fun getItemCount(): Int {
        return listaUsuarios.size
    }

    override fun onBindViewHolder(myViewHolder: AdaptadorUsuario.MyViewHolder, position: Int) {
        val usuario: Usuario = listaUsuarios[position]
        myViewHolder.usernameTextView.text = usuario.username
        myViewHolder.tipoTextView.text = usuario.tipo
        myViewHolder.idTextView.text = usuario.id.toString()

    }

    fun crearUsuario(id: Int, username: String, tipo: String): Usuario {
        val usuario = Usuario(
            id,
            username,
            null,
            tipo
        )
        return usuario
    }
}