package org.sjhstudio.fastcampus.part2.chapter7

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import org.sjhstudio.fastcampus.part2.chapter7.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter7.model.BaseDateTime
import org.sjhstudio.fastcampus.part2.chapter7.model.Forecast
import org.sjhstudio.fastcampus.part2.chapter7.model.WeatherEntity
import org.sjhstudio.fastcampus.part2.chapter7.network.Network
import org.sjhstudio.fastcampus.part2.chapter7.util.GeoPointConverter
import org.sjhstudio.fastcampus.part2.chapter7.util.showToastMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val locationRequest by lazy {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()
    }

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
                getWeather(result.lastLocation!!.latitude, result.lastLocation!!.longitude)
                stopLocationUpdates()
            }
        }
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

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        startLocationUpdates()
    }

    private fun getWeather(latitude: Double, longitude: Double) {
        val baseDateTime = BaseDateTime.getBaseDateTime()
        Log.e(LOG, "$baseDateTime")
        val geoPoint = GeoPointConverter.convert(latitude, longitude)
        Log.e(LOG, "${geoPoint.x} ${geoPoint.y}")

        Network.getWeatherService().getWeather(
            baseDate = BaseDateTime.getBaseDateTime().baseDate,
            baseTime = BaseDateTime.getBaseDateTime().baseTime,
            nx = geoPoint.x,
            ny = geoPoint.y
        ).enqueue(object : Callback<WeatherEntity> {
            override fun onResponse(call: Call<WeatherEntity>, response: Response<WeatherEntity>) {
                if (response.isSuccessful) {
                    val forecastEntities = response.body()?.response?.body?.items
                    forecastEntities?.let { entities ->
                        val list = entities.toForecastList()
                        Log.e(LOG, "$list")
                        updateWeatherUi(list)
                    }
                } else {
                    Log.e(LOG, "Network error")
                }
            }

            override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun updateWeatherUi(forecastList: List<Forecast>) {
        if (forecastList.isEmpty()) return

        with(binding) {
            tvTmp.text = getString(R.string.format_temperature, forecastList.first().tmp)
            tvSkyPty.text = forecastList.first().skyPty
            tvPop.text = getString(R.string.format_pop, forecastList.first().pop)
        }
    }
}