package com.example.proyecto

import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class AdaptadorPerro (
    private val listaPerros: ArrayList<Perro>,
    private val contexto: GestionarPerroActivity,
    private val recyclerView: androidx.recyclerview.widget.RecyclerView/*,
    private val listaPersonas: ArrayList<Persona>,
    private val listaTiendas: ArrayList<TiendaMascotas>,
    private val listaRazas: ArrayList<RazaPerro>
*/
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AdaptadorPerro.MyViewHolder>() {



    inner class MyViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {


        var idTextView: TextView
        var sexoTextView: TextView
        var edadTextView: TextView
        var colorTextView: TextView
        var nombreRazaTextView: TextView
        var cedulaPersonaTextView: TextView
        var idTiendaMascotasTextView: TextView
        var eliminarBoton: Button
        var actualizarBoton: Button

        private var listaPersonas: ArrayList<Persona>
        private var listaTiendas: ArrayList<TiendaMascotas>
        private var listaRazas: ArrayList<RazaPerro>


        init {

            listaTiendas = arrayListOf<TiendaMascotas>()
            listaPersonas = arrayListOf<Persona>()
            listaRazas = arrayListOf<RazaPerro>()

            contexto.getListaTiendas()
            contexto.getListaPersonas()
            contexto.getListaRazas()

            idTextView = view.findViewById(R.id.txt_id_perro_ly) as TextView
            sexoTextView = view.findViewById(R.id.txt_sexo_perro_ly) as TextView
            edadTextView = view.findViewById(R.id.txt_edad_perro_ly) as TextView
            colorTextView = view.findViewById(R.id.txt_color_perro_ly) as TextView
            nombreRazaTextView = view.findViewById(R.id.txt_nombre_raza_perro_ly) as TextView
            cedulaPersonaTextView = view.findViewById(R.id.txt_cedula_persona_ly) as TextView
            idTiendaMascotasTextView = view.findViewById(R.id.txt_id_tienda_mascotas_ly) as TextView
            eliminarBoton = view.findViewById(R.id.btn_eliminar_perro) as Button
            actualizarBoton = view.findViewById(R.id.btn_modificar_perro) as Button

            val layout = view.findViewById(R.id.linear_layout_usuario) as LinearLayout
            layout.setOnClickListener {
                contexto.eliminarPerro(idTextView.text.toString().toInt())

            }

            actualizarBoton.setOnClickListener {
                val razaPerro = listaRazas.find { razaPerro ->
                    razaPerro.nombreRaza == nombreRazaTextView.text.toString()
                }

                val persona = listaPersonas.find { persona ->
                    persona.cedula == cedulaPersonaTextView.text.toString()
                }

                val tiendaMascotas = listaTiendas.find { tiendaMascotas ->
                    tiendaMascotas.id == idTiendaMascotasTextView.text.toString().toInt()
                }
                val perro = Perro(
                    null,
                    sexoTextView.text.toString(),
                    edadTextView.text.toString(),
                    colorTextView.text.toString(),
                    /*razaPerro!!.id,
                    persona!!.id,
                    tiendaMascotas!!.id*/
                    PerrosXRaza(razaPerro!!.id, null),
                    PerrosXPersona(persona!!.id, null, null, null, null),
                    PerrosXTiendaMascotas(idTiendaMascotasTextView.text.toString().toInt(), null, null, null, null, null)

                )
                contexto.irAActulizarPerro(perro)

            }

            eliminarBoton.setOnClickListener {
                contexto.eliminarPerro(idTextView.text.toString().toInt())

            }
        }

    }

    //Esta función define el template que vamos a utilizar.
    // El template esta en la carpeta de recursos res/layout -> layout
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdaptadorPerro.MyViewHolder {
        val itemView = LayoutInflater
            .from(p0.context)
            .inflate(
                R.layout.layout_perro,
                p0,
                false
            )
        return MyViewHolder(itemView)
    }

    //Devuelve el número de items de la lista
    override fun getItemCount(): Int {
        return listaPerros.size
    }

    override fun onBindViewHolder(myViewHolder: AdaptadorPerro.MyViewHolder, position: Int) {
        val perro: Perro = listaPerros[position]
        myViewHolder.idTextView.text = perro.id.toString()
        myViewHolder.sexoTextView.text = perro.sexo
        myViewHolder.edadTextView.text = perro.edad
        myViewHolder.colorTextView.text = perro.color
        //myViewHolder.nombreRazaTextView.text = perro.idRaza!!.id.toString()
        //myViewHolder.cedulaPersonaTextView.text = perro.idPersona!!.id.toString()
        myViewHolder.nombreRazaTextView.text = perro.idRaza!!.nombreRaza
        myViewHolder.cedulaPersonaTextView.text = perro.idPersona!!.cedula
        myViewHolder.idTiendaMascotasTextView.text = perro.idTienda!!.id.toString()

    }


}
