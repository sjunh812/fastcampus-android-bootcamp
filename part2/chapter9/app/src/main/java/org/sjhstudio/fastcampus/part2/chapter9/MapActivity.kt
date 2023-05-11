package org.sjhstudio.fastcampus.part2.chapter9

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
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
    private var trackingUserUid: String? = null

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

                    Firebase.database.reference.child(DB_USER).child(uid)
                        .updateChildren(locationMap)
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
                if (location == null) return@addOnSuccessListener

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
                    val userPosition = LatLng(user.latitude ?: 0.0, user.longitude ?: 0.0)

                    if (!markerMap.containsKey(userUid)) {
                        markerMap[userUid] = createMarker(user) ?: return
                    } else {
                        markerMap[userUid]?.position = userPosition
                    }

                    if (userUid == trackingUserUid) {
                        googleMap.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition.Builder()
                                    .target(userPosition)
                                    .zoom(16.0f)
                                    .build()
                            )
                        )
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
        )?.apply {
            tag = user.uid.orEmpty()
        }

        Glide.with(this)
            .asBitmap()
            .load(user.profilePhoto)
            .transform(RoundedCorners(60))
            .override(200)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return true
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    runOnUiThread {
                        resource?.let { bitmap ->
                            marker?.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
                        }
                    }
                    return true
                }

            }).submit()

        return marker
    }

    private fun navigateLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.apply {
            setMinZoomPreference(10.0f)
            setMaxZoomPreference(20.0f)
            setOnMarkerClickListener { marker ->
                trackingUserUid = marker.tag as? String
                // true: 기본 클릭이벤트 무시, false: 기본 클릭이벤트 소비
                false
            }
            setOnCameraMoveStartedListener { reason ->
                Log.e(LOG, "camera move started!!")
                if (reason == REASON_GESTURE) trackingUserUid = null
            }
//            setOnMapClickListener {
//                trackingUserUid = null
//            }
//            setOnMapLongClickListener {
//                trackingUserUid = null
//            }
        }
    }
}