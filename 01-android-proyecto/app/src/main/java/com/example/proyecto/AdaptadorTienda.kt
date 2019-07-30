package com.example.proyecto

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class AdaptadorTienda(
    private val listaTiendas: ArrayList<TiendaMascotas>,
    private val contexto: GestionarTiendaActivity,
    private val recyclerView: androidx.recyclerview.widget.RecyclerView
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AdaptadorTienda.MyViewHolder>() {

    inner class MyViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var nombreTextView: TextView
        var direccionTextView: TextView
        var telefonoTextView: TextView
        var latitudTextView: TextView
        var longitudTextView: TextView
        var idTextView: TextView
        var eliminarButton: Button
        var modificarButton: Button

        init {
            nombreTextView = view.findViewById(R.id.txt_nombre_tienda_ly) as TextView
            direccionTextView = view.findViewById(R.id.txt_direccion_tienda_ly) as TextView
            telefonoTextView = view.findViewById(R.id.txt_telefono_tienda_ly) as TextView
            latitudTextView = view.findViewById(R.id.txt_latitud_tienda_ly) as TextView
            longitudTextView = view.findViewById(R.id.txt_longitud_tienda_ly) as TextView
            idTextView = view.findViewById(R.id.txt_id_tienda_ly) as TextView
            eliminarButton = view.findViewById(R.id.btn_eliminar_tienda) as Button
            modificarButton = view.findViewById(R.id.btn_modificar_tienda) as Button

            val layout = view.findViewById(R.id.linear_layout_persona) as LinearLayout
            layout.setOnClickListener {
                /*val persona = listaPersonas.find{ persona ->
                    idTextView.text.toString().toInt() == persona.id
                }

                contexto.irAActulizarPersona(persona)*/

            }

            eliminarButton.setOnClickListener {
                contexto.eliminarTienda(idTextView.text.toString().toInt())

            }

            modificarButton.setOnClickListener {
                val tiendaMascotas = TiendaMascotas(
                    idTextView.text.toString().toInt(),
                    nombreTextView.text.toString(),
                    direccionTextView.text.toString(),
                    telefonoTextView.text.toString(),
                    latitudTextView.text.toString(),
                    longitudTextView.text.toString()
                )
                contexto.irAActulizarTienda(tiendaMascotas)

            }
        }

    }

    //Esta función define el template que vamos a utilizar.
    // El template esta en la carpeta de recursos res/layout -> layout
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdaptadorTienda.MyViewHolder {
        val itemView = LayoutInflater
            .from(p0.context)
            .inflate(
                R.layout.layout_tienda,
                p0,
                false
            )
        return MyViewHolder(itemView)
    }

    //Devuelve el # de items de la lista
    override fun getItemCount(): Int {
        return listaTiendas.size
    }


    override fun onBindViewHolder(myViewHolder: AdaptadorTienda.MyViewHolder, position: Int) {
        val tiendaMascotas: TiendaMascotas = listaTiendas[position]
        myViewHolder.nombreTextView.text = tiendaMascotas.nombre
        myViewHolder.direccionTextView.text = tiendaMascotas.direccion
        myViewHolder.telefonoTextView.text = tiendaMascotas.telefono
        myViewHolder.latitudTextView.text = tiendaMascotas.latitud
        myViewHolder.longitudTextView.text = tiendaMascotas.longitud
        myViewHolder.idTextView.text = tiendaMascotas.id.toString()

    }

    fun crearTienda(id: Int,
                     nombre: String,
                    direccion: String,
                    telefono: String,
                    latitud: String,
                    longitud: String): TiendaMascotas {
        val tiendaMascotas = TiendaMascotas(
            id,
            nombre,
            direccion,
            telefono,
            latitud,
            longitud
        )
        return tiendaMascotas
    }
}