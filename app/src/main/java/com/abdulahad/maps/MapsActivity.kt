package com.abdulahad.maps

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.abdulahad.maps.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        binding.btnLocation.setOnClickListener {
            if (mMap.mapType == GoogleMap.MAP_TYPE_NORMAL) {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            } else {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
        }

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

        val cameraPosition = CameraPosition.Builder()
            .target(sydney)
            .tilt(60f)
            .zoom(15f)
            .bearing(30f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                if (it != null) {
                    val myLatLng = LatLng(it.latitude, it.longitude)
                    mMap.addMarker(MarkerOptions().position(myLatLng).title("Marker in Sydney"))
                    val camera = CameraPosition.Builder()
                        .target(myLatLng)
                        .zoom(15f)
                        .bearing(it.bearing)
                        .build()

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera))
                }
            }
    }
}