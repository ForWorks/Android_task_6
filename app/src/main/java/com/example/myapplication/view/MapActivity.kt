package com.example.myapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.app.App
import com.example.myapplication.databinding.ActivityMapBinding
import com.example.myapplication.model.ItemModel
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Constants.Companion.CENTER
import com.example.myapplication.utils.Constants.Companion.POINT
import com.example.myapplication.viewmodel.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java) }
    private lateinit var map: GoogleMap
    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        (application as App).getAppComponent().inject(this)
        map.addMarker(MarkerOptions().position(POINT).title(CENTER))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(POINT, 14f))

        viewModel.getItems().observe(this) {
            addMarkers(it)
        }
    }

    private fun addMarkers(items: List<ItemModel>) {
        items.forEach {
            val marker = MarkerOptions()
                .position(LatLng(it.x, it.y))
                .snippet(it.cityType + " " + it.city + " " + it.house)
            when (it.type) {
                0 -> {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    marker.title(Constants.ATM)
                }
                1 -> {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    marker.title(Constants.KIOSK)
                }
                2 -> {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                    marker.title(Constants.BANK)
                }
            }
            map.addMarker(marker)
        }
    }
}