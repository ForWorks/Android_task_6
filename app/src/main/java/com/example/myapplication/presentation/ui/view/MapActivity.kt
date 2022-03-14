package com.example.myapplication.presentation.ui.view

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.presentation.di.App
import com.example.myapplication.databinding.ActivityMapBinding
import com.example.myapplication.data.model.ItemModel
import com.example.myapplication.domain.utils.Constants.Companion.ATM
import com.example.myapplication.domain.utils.Constants.Companion.BANK
import com.example.myapplication.domain.utils.Constants.Companion.CENTER
import com.example.myapplication.domain.utils.Constants.Companion.CHECK_CONNECTION
import com.example.myapplication.domain.utils.Constants.Companion.KIOSK
import com.example.myapplication.domain.utils.Constants.Companion.POINT
import com.example.myapplication.presentation.ui.viewmodel.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java) }
    private lateinit var map: GoogleMap
    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }
    private val info by lazy { Snackbar.make(binding.root, CHECK_CONNECTION, Snackbar.LENGTH_INDEFINITE) }
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.addMarker(MarkerOptions().position(POINT).title(CENTER))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(POINT, 14f))

        (application as App).getAppComponent().inject(this)
        viewModel.items.observe(this) {
            addMarkers(it)
        }
    }

    private fun addMarkers(items: List<ItemModel>) {
        items.forEach {
            val marker = MarkerOptions()
                .position(LatLng(it.x, it.y))
                .snippet(it.cityType + " " + it.city + " " + it.house)
            when (it.type) {
                ATM -> {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    marker.title(ATM)
                }
                KIOSK -> {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    marker.title(KIOSK)
                }
                BANK -> {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                    marker.title(BANK)
                }
            }
            map.addMarker(marker)
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.activeNetwork != null)
            return true
        return false
    }

    private val showInfo = object : Runnable {
        override fun run() {
            if (isOnline()) {
                if(viewModel.items.value.isNullOrEmpty()) {
                    viewModel.fillItemList()
                }
                if(info.isShown)
                    info.dismiss()
            }
            else
                if(!info.isShown)
                    info.show()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onStart() {
        super.onStart()
        handler.post(showInfo)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(showInfo)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(showInfo)
    }
}