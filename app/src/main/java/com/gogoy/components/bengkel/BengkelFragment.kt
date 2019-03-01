package com.gogoy.components.bengkel

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gogoy.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class BengkelFragment : Fragment(), BengkelContract.View, OnMapReadyCallback {

    override lateinit var presenter: BengkelContract.Presenter
    lateinit var googleMap: GoogleMap
    private lateinit var markerOptions: MarkerOptions
    private lateinit var marker: Marker
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onMapReady(gmap: GoogleMap?) {
        val activity = context as Activity

        googleMap = gmap!!
        googleMap.isMyLocationEnabled = true

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient.lastLocation.addOnSuccessListener(activity) { location ->

            val heightMarker = 200
            val widthMarker = 200
            val bitmapDraw = ContextCompat.getDrawable(activity, R.drawable.gogoy) as BitmapDrawable
            val bipMap = bitmapDraw.bitmap
            val bitmapMarker = Bitmap.createScaledBitmap(bipMap, widthMarker, heightMarker, false)
            markerOptions = MarkerOptions()

            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)

                markerOptions.position(currentLatLng)
                    .flat(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmapMarker))

                marker = googleMap.addMarker(markerOptions)

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            } else {
                //default marker in Jakarta
                val jakarta = LatLng(-6.21462, 106.84513)

                markerOptions.position(jakarta)
                    .flat(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmapMarker))

                marker = googleMap.addMarker(markerOptions)

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jakarta, 15f))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_bengkel_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start presenter
        presenter.start()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun showMaps() {
        println("TODO: showMaps")
    }

    override fun showSearch() {
        println("TODO: showSearch")
    }

    companion object {
        fun newInstance() = BengkelFragment()
    }
}