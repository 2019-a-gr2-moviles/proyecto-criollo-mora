package com.example.proyecto

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class AdaptadorRazaPerro (
    private val listaRazas: ArrayList<RazaPerro>,
    private val contexto: GestionarRazaPerroActivity,
    private val recyclerView: androidx.recyclerview.widget.RecyclerView
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AdaptadorRazaPerro.MyViewHolder>() {

    inner class MyViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var nombreRazaTextView: TextView
        var idTextView: TextView
        var eliminarButton: Button
        var modificarButton: Button

        init {
            nombreRazaTextView = view.findViewById(R.id.txt_nombre_raza_ly) as TextView
            idTextView = view.findViewById(R.id.txt_id_raza_ly) as TextView
            eliminarButton = view.findViewById(R.id.btn_eliminar_raza) as Button
            modificarButton = view.findViewById(R.id.btn_modificar_raza) as Button

            val layout = view.findViewById(R.id.linear_layout_raza) as LinearLayout
            layout.setOnClickListener {
                contexto.eliminarRaza(idTextView.text.toString().toInt())

            }

            eliminarButton.setOnClickListener {
                contexto.eliminarRaza(idTextView.text.toString().toInt())

            }

            modificarButton.setOnClickListener {
                val razaPerro = RazaPerro(
                    idTextView.text.toString().toInt(),
                    nombreRazaTextView.text.toString()

                )
                contexto.irAActulizarRaza(razaPerro)

            }
        }

    }

    //Esta función define el template que vamos a utilizar.
    // El template esta en la carpeta de recursos res/layout -> layout
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdaptadorRazaPerro.MyViewHolder {
        val itemView = LayoutInflater
            .from(p0.context)
            .inflate(
                R.layout.layout_raza_perro,
                p0,
                false
            )
        return MyViewHolder(itemView)
    }

    //Devuelve el # de items de la lista
    override fun getItemCount(): Int {
        return listaRazas.size
    }


    override fun onBindViewHolder(myViewHolder: AdaptadorRazaPerro.MyViewHolder, position: Int) {
        val razaPerro : RazaPerro = listaRazas[position]
        myViewHolder.nombreRazaTextView.text = razaPerro.nombreRaza
        myViewHolder.idTextView.text = razaPerro.id.toString()

    }

    /*fun crearPersona(id: Int,
                     nombre: String,
                     apellido: String,
                     cedula: String,
                     fechaNac: String): Persona {
        val persona = Persona(
            id,
            nombre,
            apellido,
            cedula,
            fechaNac
        )
        return persona
    }*/
}