package com.example.androidintermadedicoding.ui.map

import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androidintermadedicoding.R
import com.example.androidintermadedicoding.data.model.ResponseAllStory
import com.example.androidintermadedicoding.data.view_model.AuthenticationFactory
import com.example.androidintermadedicoding.data.view_model.StoryViewModel
import com.example.androidintermadedicoding.databinding.ActivityMapBinding
import com.example.androidintermadedicoding.ui.DetailActivity
import com.example.androidintermadedicoding.utils.Status
import com.example.androidintermadedicoding.utils.preference.PreferenceFactory
import com.example.androidintermadedicoding.utils.preference.PreferenceViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private val authenticationFactory: AuthenticationFactory by inject()
    private val storyViewModel: StoryViewModel by viewModels { authenticationFactory }

    private val prefFactory: PreferenceFactory by inject()
    private val pref: PreferenceViewModel by viewModels{prefFactory}

    private var responseAllLocation: ResponseAllStory? = null

    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setSupportActionBar(binding.mapToolbar)
        supportActionBar?.title = "Daftar Lokasi"

        pref.getBearerKey().observe(this@MapActivity){
            storyViewModel.getAllLocation(it).observe(this@MapActivity){status ->
                when(status){

                    is Status.Loading ->{
                        binding.map.visibility = View.GONE
                        binding.pbLoading.visibility = View.VISIBLE
                    }

                    is Status.Success ->{
                        binding.map.visibility = View.VISIBLE
                        binding.pbLoading.visibility = View.GONE

                        val data = status.data

                        responseAllLocation = data

                        responseAllLocation?.listStory?.forEach { location ->
                            val latLng = location?.let { LatLng(it.lat ?: 0.0, it.lon ?: 0.0) }

                            if (latLng != null) {
                                mMap.addMarker(MarkerOptions().position(latLng).title(location.name))
                                boundsBuilder.include(latLng)
                            }
                        }

                        val bounds: LatLngBounds = boundsBuilder.build()

                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                bounds,
                                resources.displayMetrics.widthPixels,
                                resources.displayMetrics.heightPixels,
                                300
                            )
                        )


                        Toast.makeText(
                            this@MapActivity,
                            data.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Status.Error -> {
                        binding.map.visibility = View.VISIBLE
                        binding.pbLoading.visibility = View.GONE

                        val data = status.error

                        Log.e(DetailActivity.nameClass, data)

                        Toast.makeText(this@MapActivity, "Some Think Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

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

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
        setMapStyle()
        getMyLocation()
    }

    private fun setMapStyle(){
        try {
            val success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success){
                Log.e(TAG, "Style parsing failed")
            }
        }catch (exeption: Resources.NotFoundException){
            Log.e(TAG, "Can't find style. Error: ", exeption)
        }
    }

    private val requestPremissionLaucher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){ isGranted: Boolean ->
            if (isGranted){
                getMyLocation()
            }

        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED){
            mMap.isMyLocationEnabled = true
        }else{
            requestPremissionLaucher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    companion object {
        private const val TAG = "MapsActivity"
    }
}