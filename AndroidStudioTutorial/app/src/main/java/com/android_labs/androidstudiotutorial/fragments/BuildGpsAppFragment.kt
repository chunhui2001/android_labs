package com.android_labs.androidstudiotutorial.fragments

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android_labs.androidstudiotutorial.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.lang.Exception

const val PERMISSION_FINE_LOCATION = 99

class BuildGpsAppFragment : Fragment() {

    private lateinit var tvLat: TextView
    private lateinit var tvLon: TextView
    private lateinit var tvAltitude: TextView
    private lateinit var tvAccuracy: TextView
    private lateinit var tvSpeed: TextView
    private lateinit var tvAddress: TextView

    private lateinit var swUpdate: Switch
    private lateinit var swGps: Switch

    private lateinit var tvUpdate: TextView
    private lateinit var tvSensor: TextView

    // LocationRequest is a config file for all settings related to FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    // Google's API for location services. The majority of the app functions using this class.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationCallback: LocationCallback
    private var updateCounts = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_build_gps_app, container, false);

        this.tvLat = view.findViewById(R.id.tvLat)
        this.tvLon = view.findViewById(R.id.tvLon)
        this.tvAltitude = view.findViewById(R.id.tvAltitude)
        this.tvAccuracy = view.findViewById(R.id.tvAccuracy)
        this.tvSpeed = view.findViewById(R.id.tvSpeed)
        this.tvAddress = view.findViewById(R.id.tvAddress)

        this.swUpdate = view.findViewById(R.id.swUpdate)
        this.swGps = view.findViewById(R.id.swGps)

        this.tvUpdate = view.findViewById(R.id.tvUpdate)
        this.tvSensor = view.findViewById(R.id.tvSensor)

        this.locationRequest = LocationRequest()
        this.locationRequest.setInterval(1000 * 30)
        this.locationRequest.setFastestInterval(1000 * 5)
        this.locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

        this.swGps.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // most accurate - use GPS
                this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                this.tvSensor.text = "Using GPS sensors"
            } else {
                this.locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                this.tvSensor.text = "Using Towers + Wifi"
            }
        }

        this.swUpdate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // turn on location tracking
                startLocationUpdates()
            } else {
                // turn off tracking
                stopLocationUpdates()
            }
        }

        this.locationCallback = object: LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                p0.lastLocation?.let {
                    updateUiValues(it)
                }
            }
        }

        updateGPS();

        return view
    }

    private fun stopLocationUpdates() {
        this.tvUpdate.text = "Off"
        this.fusedLocationProviderClient.removeLocationUpdates(this.locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        this.tvUpdate.text = "On"
        this.fusedLocationProviderClient.requestLocationUpdates(this.locationRequest, this.locationCallback, null)
        updateGPS()
    }

    @SuppressLint("MissingPermission")
    fun updateGPS() {
        // get permissions from the user to track GPS
        // get the current location from the fused client
        // update the UI - i.e. set all properties in their associated text view items.
        this.fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        if (hasPermission(requireActivity(), listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))) {
            // use the provided the permission
            this.fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                updateUiValues(it)
            }
        } else {
            // permission not granted yet.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission(
                    requireActivity(),
                    listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
                    PERMISSION_FINE_LOCATION
                )
            }
        }
    }

    private fun updateUiValues(location: Location) {
        this.tvLat.text = location.latitude.toString()
        this.tvLon.text = location.longitude.toString()

        if (location.hasAccuracy()) {
            this.tvAccuracy.text = location.accuracy.toString()
        } else {
            this.tvAccuracy.text = "Not available"
        }

        if (location.hasAltitude()) {
            this.tvAltitude.text = location.altitude.toString()
        } else {
            this.tvAltitude.text = "Not available"
        }

        if (location.hasSpeed()) {
            this.tvSpeed.text = location.speed.toString()
        } else {
            this.tvSpeed.text = "Not available"
        }

        try {
            var geo = Geocoder(requireActivity())
            var address = geo.getFromLocation(location.latitude, location.longitude, 1)
            var countryName = address?.get(0)?.countryName
            var countryCode = address?.get(0)?.countryCode
            this.tvAddress.text = countryName + "[" + countryCode + "], " + address?.get(0)?.getAddressLine(0)
        } catch (e: Exception) {
            this.tvAddress.text = "Unable to get street address"
        }

        if (this.tvUpdate.text.startsWith("On")) {
            this.updateCounts += 1
            this.tvUpdate.text = "On(${this.updateCounts})"
        }
    }

    private fun hasPermission(activity: Activity, permission: List<String>): Boolean {
        for (p in permission) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    p
                ) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }

        return true
    }

    private fun requestPermission(activity: Activity, permission: List<String>, code: Int) {
        ActivityCompat.requestPermissions(
            activity,
            permission.toTypedArray(),
            code
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_FINE_LOCATION) {
            var allGranted = true

            for (gr in grantResults) {
                if (gr != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false
                }
            }

            if (!allGranted) {
                Toast.makeText(
                    requireContext(),
                    "This app requires permission to be granted in order to work property",
                    Toast.LENGTH_SHORT
                ).show()
                activity?.finish()
            } else {
                updateGPS()
            }

            return
        }
    }
}