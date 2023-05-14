package org.sjhstudio.fastcampus.part2.chapter9.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.airbnb.lottie.LottieAnimationView
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter9.R
import org.sjhstudio.fastcampus.part2.chapter9.databinding.ActivityMapBinding
import org.sjhstudio.fastcampus.part2.chapter9.model.Emoji
import org.sjhstudio.fastcampus.part2.chapter9.model.User
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_EMOJI
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_EMOJI_LAST_MODIFIER
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_EMOJI_TYPE
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_USER
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_USER_LATITUDE
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_USER_LONGITUDE
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.EMOJI_TYPE_SMILE
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.EMOJI_TYPE_STAR
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.EMOJI_TYPE_SUNGLASS

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
                // TODO. 설정 화면 or 교육용 팝업
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

                    Firebase.database.reference.child(DB_USER)
                        .child(uid)
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (Firebase.auth.currentUser?.uid != null) {
            uid = Firebase.auth.currentUser?.uid!!
            trackingUserUid = uid
        } else {
            navigateLoginActivity()
        }

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initViews()
        setUpFirebaseDatabase()
    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun initViews() {
        with(binding) {
            BottomSheetBehavior.from(bottomSheetEmoji).state = BottomSheetBehavior.STATE_HIDDEN

            fabCurrentLocation.setOnClickListener {
                trackingUserUid = null
                getLastLocation()
            }

            lottieViewSmileyEmoji.setOnClickListener {
                sendEmoji(EMOJI_TYPE_SMILE)
                updateDummyEmoji(lottieViewSmileyEmoji, EMOJI_TYPE_SMILE)
            }

            lottieViewStarEmoji.setOnClickListener {
                sendEmoji(EMOJI_TYPE_STAR)
                updateDummyEmoji(lottieViewStarEmoji, EMOJI_TYPE_STAR)
            }

            lottieViewSunglassEmoji.setOnClickListener {
                sendEmoji(EMOJI_TYPE_SUNGLASS)
                updateDummyEmoji(lottieViewSunglassEmoji, EMOJI_TYPE_SUNGLASS)
            }
        }
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

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        getLastLocation()
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location == null) return@addOnSuccessListener

            Log.e(LOG, "last location : ${location.latitude}, ${location.longitude}")

            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(location.latitude, location.longitude),
                    16.0f
                )
            )
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

    private fun setUpFirebaseDatabase() {
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

        Firebase.database.reference.child(DB_EMOJI)
            .child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val typeValue = snapshot.child(DB_EMOJI_TYPE).value ?: return
                    updateReceivedEmoji(typeValue.toString())
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun createMarker(user: User): Marker? {
        val marker = googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(user.latitude ?: 0.0, user.longitude ?: 0.0))
                .title(user.name.orEmpty())
                .visible(false)
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
                            marker?.isVisible = true
                        }
                    }
                    return true
                }

            }).submit()

        return marker
    }

    private fun sendEmoji(emojiType: String) {
        if (trackingUserUid.isNullOrEmpty() || trackingUserUid == uid) return

        val emojiMap = mutableMapOf<String, Any>(
            DB_EMOJI_TYPE to emojiType,
            DB_EMOJI_LAST_MODIFIER to System.currentTimeMillis()
        )

        Firebase.database.reference.child(DB_EMOJI).child(trackingUserUid!!)
            .updateChildren(emojiMap)
    }

    private fun updateDummyEmoji(emojiView: LottieAnimationView, emojiType: String) {
        emojiView.playAnimation()

        val dummyEmojiView = when (emojiType) {
            EMOJI_TYPE_SMILE -> binding.lottieViewDummySmileyEmoji
            EMOJI_TYPE_STAR -> binding.lottieViewDummyStarEmoji
            EMOJI_TYPE_SUNGLASS -> binding.lottieViewDummySunglassEmoji
            else -> return
        }

        dummyEmojiView.also { v ->
            v.scaleX = 1f
            v.scaleY = 1f
            v.alpha = 1f
        }.run {
            playAnimation()
            animate()
                .scaleX(3f)
                .scaleY(3f)
                .alpha(0f)
                .withEndAction {
                    scaleX = 1f
                    scaleY = 1f
                    alpha = 1f
                }.start()
        }
    }

    private fun updateReceivedEmoji(emojiType: String) {
        binding.lottieViewReceivedEmoji.also { v ->
            v.scaleX = 0f
            v.scaleY = 0f
            v.alpha = 1f
            v.speed = 2f
        }.run {
            setAnimation(
                when (emojiType) {
                    EMOJI_TYPE_SMILE -> R.raw.emoji_smiley
                    EMOJI_TYPE_STAR -> R.raw.emoji_star_strike
                    EMOJI_TYPE_SUNGLASS -> R.raw.emoji_sunglass
                    else -> return@run
                }
            )
            playAnimation()
            animate()
                .scaleX(5f)
                .scaleY(5f)
                .alpha(0.3f)
                .setDuration(duration / 2)
                .withEndAction {
                    scaleX = 0f
                    scaleY = 0f
                    alpha = 1f
                }.start()
        }
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

                BottomSheetBehavior.from(binding.bottomSheetEmoji).state =
                    if (uid == trackingUserUid) BottomSheetBehavior.STATE_HIDDEN else BottomSheetBehavior.STATE_EXPANDED

                false   // true: 기본 클릭이벤트 무시, false: 기본 클릭이벤트 소비
            }
            setOnCameraMoveStartedListener { reason ->
                if (reason == REASON_GESTURE) {
                    trackingUserUid = null
                    BottomSheetBehavior.from(binding.bottomSheetEmoji).state =
                        BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }
    }
}