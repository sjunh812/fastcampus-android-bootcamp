package org.sjhstudio.fastcampus.part2.chapter9

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter9.databinding.ActivityMapBinding
import org.sjhstudio.fastcampus.part2.chapter9.model.User
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_USER
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_USER_LATITUDE
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_USER_LONGITUDE

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var uid: String
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val markerMap = hashMapOf<String, Marker>()

    private val locationPermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getCurrentLocation()
            }
            permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getCurrentLocation()
            }
            else -> {
                // todo. 설정화면 or 교육용 팝업
            }
        }
    }

    private val locationRequest by lazy {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L).build()
    }
    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    Log.e(LOG, "${location.latitude} ${location.longitude}")

                    val locationMap = mutableMapOf<String, Any>(
                        DB_USER_LATITUDE to location.latitude,
                        DB_USER_LONGITUDE to location.longitude
                    )

                    Firebase.database.reference.child(DB_USER).child(uid).updateChildren(locationMap)
                }
            }
        }
    }

    companion object {
        private const val LOG = "MapActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Firebase.auth.currentUser?.uid != null) {
            uid = Firebase.auth.currentUser?.uid!!
        } else {
            navigateLoginActivity()
        }

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        requestLocationPermissions()
        updateFirebaseDatabase()
    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissions()
            return
        }

        fusedLocationProviderClient.run {
            requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            lastLocation.addOnSuccessListener { location ->
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            location.latitude,
                            location.longitude
                        ),
                        16.0f
                    )
                )
            }
        }
    }

    private fun requestLocationPermissions() {
        locationPermissionResult.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun updateFirebaseDatabase() {
        Firebase.database.reference.child(DB_USER)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val user = snapshot.getValue(User::class.java) ?: return
                    val userUid = user.uid ?: return

                    if (!markerMap.containsKey(userUid)) {
                        markerMap[userUid] = createMarker(user) ?: return
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val user = snapshot.getValue(User::class.java) ?: return
                    val userUid = user.uid ?: return

                    if (!markerMap.containsKey(userUid)) {
                        markerMap[userUid] = createMarker(user) ?: return
                    } else {
                        markerMap[userUid]?.position =
                            LatLng(user.latitude ?: 0.0, user.longitude ?: 0.0)
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun createMarker(user: User): Marker? {
        val marker = googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(user.latitude ?: 0.0, user.longitude ?: 0.0))
                .title(user.name.orEmpty())
        )

        return marker
    }

    private fun navigateLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setMinZoomPreference(10.0f)
        googleMap.setMaxZoomPreference(20.0f)
    }
}