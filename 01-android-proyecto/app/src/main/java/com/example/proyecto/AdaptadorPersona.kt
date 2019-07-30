package com.example.proyecto

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class AdaptadorPersona(
    private val listaPersonas: ArrayList<Persona>,
    private val contexto: GestionarPersonaActivity,
    private val recyclerView: androidx.recyclerview.widget.RecyclerView
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AdaptadorPersona.MyViewHolder>() {

    inner class MyViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var nombreTextView: TextView
        var apellidoTextView: TextView
        var cedulaTextView: TextView
        var fechaNacTextView: TextView
        var idTextView: TextView
        var eliminarButton: Button
        var modificarButton: Button

        init {
            nombreTextView = view.findViewById(R.id.txt_nombre_persona_ly) as TextView
            apellidoTextView = view.findViewById(R.id.txt_apellido_ly) as TextView
            cedulaTextView = view.findViewById(R.id.txt_cedula_ly) as TextView
            fechaNacTextView = view.findViewById(R.id.txt_fecha_nac_ly) as TextView
            idTextView = view.findViewById(R.id.txt_id_persona_ly) as TextView
            eliminarButton = view.findViewById(R.id.btn_eliminar_persona) as Button
            modificarButton = view.findViewById(R.id.btn_modificar_persona) as Button

            val layout = view.findViewById(R.id.linear_layout_persona) as LinearLayout
            layout.setOnClickListener {
                /*val persona = listaPersonas.find{ persona ->
                    idTextView.text.toString().toInt() == persona.id
                }

                contexto.irAActulizarPersona(persona)*/

            }

            eliminarButton.setOnClickListener {
                contexto.eliminarPersona(idTextView.text.toString().toInt())

            }

            modificarButton.setOnClickListener {
                val persona = Persona(
                    idTextView.text.toString().toInt(),
                    nombreTextView.text.toString(),
                    apellidoTextView.text.toString(),
                    cedulaTextView.text.toString(),
                    fechaNacTextView.text.toString()
                )
                contexto.irAActulizarPersona(persona)

            }
        }

    }

    //Esta función define el template que vamos a utilizar.
    // El template esta en la carpeta de recursos res/layout -> layout
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdaptadorPersona.MyViewHolder {
        val itemView = LayoutInflater
            .from(p0.context)
            .inflate(
                R.layout.layout_persona,
                p0,
                false
            )
        return MyViewHolder(itemView)
    }

    //Devuelve el # de items de la lista
    override fun getItemCount(): Int {
        return listaPersonas.size
    }


    override fun onBindViewHolder(myViewHolder: AdaptadorPersona.MyViewHolder, position: Int) {
        val persona: Persona = listaPersonas[position]
        myViewHolder.nombreTextView.text = persona.nombre
        myViewHolder.apellidoTextView.text = persona.apellido
        myViewHolder.cedulaTextView.text = persona.cedula
        myViewHolder.fechaNacTextView.text = persona.fechaNac
        myViewHolder.idTextView.text = persona.id.toString()

    }

    fun crearPersona(id: Int,
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
    }
}