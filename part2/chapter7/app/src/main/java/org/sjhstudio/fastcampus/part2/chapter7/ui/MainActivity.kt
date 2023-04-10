package org.sjhstudio.fastcampus.part2.chapter7.ui

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import org.sjhstudio.fastcampus.part2.chapter7.R
import org.sjhstudio.fastcampus.part2.chapter7.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter7.model.Forecast
import org.sjhstudio.fastcampus.part2.chapter7.repository.WeatherRepository
import org.sjhstudio.fastcampus.part2.chapter7.ui.adapter.ForecastAdapter
import org.sjhstudio.fastcampus.part2.chapter7.util.showToastMessage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val locationRequest by lazy {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()
    }
    private val forecastAdapter by lazy { ForecastAdapter() }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // 대략적위치 허용
                    getLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // 정확한위치 허용
                    getLocation()
                }
                else -> {
                    // 거부
                    showToastMessage("날씨 정보를 가져오기 위해서 위치 권한이 필요합니다.")
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .apply { data = Uri.fromParts("package", packageName, null) }
                    startActivity(intent)
                }
            }
        }

    private val resolutionResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                startLocationUpdates()
            }
        }

    companion object {
        private const val LOG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                Log.e(LOG, "${result.locations}")
                result.lastLocation ?: return

                WeatherRepository.getAddress(
                    context = this@MainActivity,
                    latitude = result.lastLocation!!.latitude,
                    longitude = result.lastLocation!!.longitude
                ) { address ->
                    updateAddressUi(address)
                }

                WeatherRepository.getVillageForecast(
                    latitude = result.lastLocation!!.latitude,
                    longitude = result.lastLocation!!.longitude,
                    successCallback = { forecastList ->
                        Log.e(LOG, "$forecastList")
                        updateWeatherUi(forecastList)
                    },
                    failureCallback = { t ->
                        t.printStackTrace()
                    }
                )
//                getAddress(result.lastLocation!!.latitude, result.lastLocation!!.longitude)
//                getWeather(result.lastLocation!!.latitude, result.lastLocation!!.longitude)
                stopLocationUpdates()
            }
        }

        initViews()
    }

    override fun onResume() {
        super.onResume()
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun initViews() {
        with(binding) {
            rvForecast.apply {
                adapter = forecastAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            }
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()
        val settingsClient = LocationServices.getSettingsClient(this)

        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    Log.e(LOG, "ResolvableApiException")
                    try {
                        resolutionResultLauncher.launch(
                            IntentSenderRequest.Builder(exception.resolution).build()
                        )
                    } catch (sendException: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                } else {
                    Log.e(LOG, "??")
                }
            }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun updateAddressUi(address: Address?) {
        binding.tvLocation.text = address?.thoroughfare.orEmpty()
    }

    private fun updateWeatherUi(forecastList: List<Forecast>) {
        with(binding) {
            tvTmp.text = getString(R.string.format_temperature, forecastList.first().tmp)
            tvSkyPty.text = forecastList.first().skyPty
            tvPop.text = getString(R.string.format_pop, forecastList.first().pop)
            rvForecast.isVisible = true
        }

        forecastAdapter.submitList(forecastList)
    }
}