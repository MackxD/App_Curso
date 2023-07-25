package com.example.app_curso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.File

class Conctacto : AppCompatActivity() {
    private lateinit var map: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conctacto)



        val osmConfig = Configuration.getInstance()
        osmConfig.userAgentValue = packageName
        osmConfig.osmdroidBasePath = cacheDir
        osmConfig.osmdroidTileCache = File(cacheDir, "tiles")

        //Inicializar el mapa
        map = findViewById(R.id.map)
        map.setMultiTouchControls(true)

        //Agregar capa de ubicacion
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this),map)
        locationOverlay.enableMyLocation()
        map.overlays.add(locationOverlay)

        // Especificar las coordenadas de la ubicación que deseas mostrar
        val latitude = 21.1247 // Latitud de la ubicación
        val longitude = -101.60182 // Longitud de la ubicación
        val geoPoint = GeoPoint(latitude, longitude)

        val marker = Marker(map)
        marker.position = geoPoint
        marker.title = "IECA"
        marker.snippet = "Centro de capacitacion"

            map.overlays.add(marker)
            map.controller.setCenter(geoPoint)
            map.controller.setZoom(18.0)

        }
    }
