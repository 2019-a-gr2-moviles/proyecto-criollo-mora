package com.example.proyecto

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var tienePermisosLocalizacion = false
    //private var listaTiendaMascotas : ArrayList<TiendaMascotas> = arrayListOf()

    /*init {
        listaTiendaMascotas = arrayListOf<TiendaMascotas>()
        Log.i("http", "Tienda.size1: ${listaTiendaMascotas.size}")
        getListaTiendas()
        Log.i("http", "Tienda.size2: ${listaTiendaMascotas.size}")
        listaTiendaMascotas = getListaTiendas()

    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        solicitarPermisosLocalizacion()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        establecerConfiguracionMapa(mMap)
        //establecerListenersMovimientoMapa(mMap)

        // Add a marker in Sydney and move the camera
        /*val sydney = LatLng(-0.202760, -78.490813)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        /*val foch = LatLng(-0.202760, -78.490813)
        val zoom = 15f
        //var listaTiendas : ArrayList<TiendaMascotas> = arrayListOf()

        anadirMarcador(foch, "Ubicacion")
        moverCamaraConZoom(foch, zoom)*/
        //getListaTiendas()
        /*listaTiendaMascotas.forEach { tiendaMascotas ->
            Log.i("http", "Tienda: ${tiendaMascotas.nombre}")
            Log.i("http", "Tienda.size: ${listaTiendaMascotas.size}")
            var latitud = tiendaMascotas.latitud.toDouble()
            var longitud = tiendaMascotas.longitud.toDouble()
            anadirMarcador(LatLng(latitud, longitud), tiendaMascotas.nombre)
        }
        Log.i("http", "Tienda.size: ${listaTiendaMascotas.size}")*/

       /* listaTiendaMascotas = getListaTiendas()
        Log.i("http", "Tienda.size: ${listaTiendaMascotas.size}")

        listaTiendaMascotas.forEach { tiendaMascotas ->
            Log.i("http", "Tienda: ${tiendaMascotas.nombre}")
            var latitud = tiendaMascotas.latitud.toDouble()
            var longitud = tiendaMascotas.longitud.toDouble()
            anadirMarcador(LatLng(latitud, longitud), tiendaMascotas.nombre)
        }
        Log.i("http", "Tienda.size: ${listaTiendaMascotas.size}")*/



        Variables.listaTiendaMascotas.forEach { tiendaMascotas ->
            Log.i("http", "${tiendaMascotas.nombre}")
            var latitud = tiendaMascotas.latitud.toDouble()
            var longitud = tiendaMascotas.longitud.toDouble()
            anadirMarcador(LatLng(latitud, longitud), tiendaMascotas.nombre)
        }

    }

    fun solicitarPermisosLocalizacion() {
        val permisoFineLocation = ContextCompat.checkSelfPermission(
            this.applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        val tienePermiso = permisoFineLocation == PackageManager.PERMISSION_GRANTED

        if (tienePermiso) {
            Log.i("mapa", "Tiene permisos de FINE_LOCATION")
            this.tienePermisosLocalizacion = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1 //Codigo que vamos a esperar
            )
        }
    }

    fun anadirMarcador(latLng: LatLng, title: String) {
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }

    /*fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f) {
        mMap.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )
    }*/

    fun establecerConfiguracionMapa(mapa: GoogleMap) {
        val contexto = this.applicationContext
        with(mapa) {

            val permisoFineLocation = ContextCompat.checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            val tienePermiso = permisoFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermiso) {
                mapa.isMyLocationEnabled = true
            }


            this.uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    /*fun getListaTiendas(): ArrayList<TiendaMascotas> {
        //val listaTiendas: ArrayList<TiendaMascotas> = arrayListOf()
        try {
            val url = "http://192.168.1.2:1337/tiendaMascotas"
            Log.i("http", url)
            url.httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http", "Error1: ${ex.message}")
                        }
                        is Result.Success -> {
                            val data = result.get()
                            Log.i("http", "Data: ${data}")

                            val tiendas = Klaxon()
                                .parseArray<TiendaMascotas>(data)

                            tiendas?.forEach { tiendaMascotas ->
                                /*(
                                        this.listaTiendaMascotas.add(tiendaMascotas)
                                        )*/
                                listaTiendaMascotas.add(tiendaMascotas)
/*
                                var latitud = tiendaMascotas.latitud.toDouble()
                                var longitud = tiendaMascotas.longitud.toDouble()
                                Log.i("http", "Tienda: ${tiendaMascotas.nombre}")
                                Log.i("http", "Tienda: ${tiendaMascotas.latitud.toDouble()}")
                                Log.i("http", "Tienda: ${tiendaMascotas.longitud.toDouble()}")
                                Log.i("http", "Tienda: ${latitud}")
                                Log.i("http", "Tienda: ${longitud}")
                                Log.i("http", "a\n")
                                anadirMarcador(LatLng(latitud, longitud), tiendaMascotas.nombre)*/
                            }
                        }
                    }
                }
        } catch (ex: Exception) {
            Log.i("http", "Error2: ${ex.message}")
        }
        return listaTiendaMascotas
    }*/




}
