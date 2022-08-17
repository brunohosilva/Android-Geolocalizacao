package com.example.androidgeolocalizao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

// criado classe com seus respectivos parametros a ser recebido
data class Place(
    val name: String,
    val latLng: LatLng,
    val address: String,
    val rating: Float,
)

class MainActivity : AppCompatActivity() {
    // Criado uma lista utilizando a classe a cima e populando com dados
    private val places = arrayListOf(
        Place("FIAP Campus Vila Olimpia", LatLng(-23.5955843, -46.6851937), "Rua Olimpiadas, 186 - São Paulo - SP", 4.8f),
        Place("FIAP Campus Paulista", LatLng(-23.5643721, -46.652857), "Av. Paulista, 1106 - São Paulo - SP", 5.0f),
        Place("FIAP Campus Vila Mariana", LatLng(-23.5746685, -46.6232043), "Av. Lins de Vasconcelos, 1264 - São Paulo - SP", 4.8f)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // acessando o fragmento de mapa e incluindo o mapa do google maps nela
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync{
            googleMap -> addMarkers(googleMap)

            googleMap.setOnMapLoadedCallback{
                // cria um bounding box com as coordernada recebidas
                val bounds = LatLngBounds.builder()
                places.forEach{
                    bounds.include(it.latLng)
                }
                // faz redirecionamento do mapa para as coordenadas setadas
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
            }
        }
    }


    // funcao que adiciona marker ao mapa
    private fun addMarkers(googleMap: GoogleMap){
        // feito um mapeado do dados do array
        places.forEach{
                place ->
            val marker = googleMap.addMarker(
                // pega cada dado mapeado do array e inclui um marker na posicao
                MarkerOptions()
                    .title(place.name)
                    .snippet(place.address)
                    .position(place.latLng)
                        // Utilizando um helper para converter um svg para um bitmap
                    .icon(BitmapHelper.vectorToBitmap(
                        this,R.drawable.ic_education_svgrepo_com,
                        ContextCompat.getColor(this, R.color.purple_700)
                    ))
            )
        }
    }
}

