package org.sjhstudio.fastcampus.part2.chapter9

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.sjhstudio.fastcampus.part2.chapter9.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        startActivity(Intent(this, LoginActivity::class.java))

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Marker example
        // 고양대로 719-12
        // X :126.776859, Y :37.6836878
        val homeLatLng = LatLng(37.6836878, 126.776859)
        googleMap.addMarker(
            MarkerOptions()
                .position(homeLatLng)
                .title("Marker in my home")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(homeLatLng))
    }
}