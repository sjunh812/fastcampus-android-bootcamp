package org.sjhstudio.fastcampus.part2.chapter8.ui

import android.accounts.NetworkErrorException
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.text.HtmlCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import org.sjhstudio.fastcampus.part2.chapter8.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter8.model.Restaurant
import org.sjhstudio.fastcampus.part2.chapter8.network.model.SearchResult
import org.sjhstudio.fastcampus.part2.chapter8.repository.SearchRepository
import org.sjhstudio.fastcampus.part2.chapter8.ui.adapter.RestaurantAdapter
import org.sjhstudio.fastcampus.part2.chapter8.util.showToastMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var naverMap: NaverMap

    private val restaurantAdapter by lazy {
        RestaurantAdapter { position ->
            moveCamera(position, 17.0)
        }
    }

    private var markers = emptyList<Marker>()

    companion object {
        private const val LOG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@MainActivity)
        }

        initViews()
    }

    private fun initViews() {
        with(binding) {
            searchView.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    search(query ?: return false)
                    // true:키보드유지, false:키보드내림
                    return false
                }

                override fun onQueryTextChange(newText: String?) = true
            })
            layoutBottomSheet.rvRestaurant.adapter = restaurantAdapter

            BottomSheetBehavior.from(layoutBottomSheet.root)
                .apply {
                    state = BottomSheetBehavior.STATE_COLLAPSED
                    addBottomSheetCallback(object : BottomSheetCallback() {
                        override fun onStateChanged(bottomSheet: View, newState: Int) {

                        }

                        override fun onSlide(bottomSheet: View, slideOffset: Float) {
                            layoutBottomSheet.rvRestaurant.alpha =
                                (slideOffset * 2).coerceAtMost(1f)
                        }
                    })
                }
        }
    }

    private fun search(query: String) {
        SearchRepository.getRestaurant(query).enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                try {
                    val res = response.takeIf { it.isSuccessful } ?: throw NetworkErrorException()
                    val items = res.body()?.items.orEmpty()

                    Log.e(LOG, "$items")

                    if (items.isEmpty()) showToastMessage("검색 결과가 없습니다.")
                    else {
                        val restaurants = items.map { it.toRestaurant() }
                        restaurantAdapter.submitList(restaurants)
                        updateMarkers(restaurants)
                    }
                } catch (e: NetworkErrorException) {
                    Log.e(LOG, e.toString())
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                showToastMessage("서버와 연결상태가 좋지 않습니다.")
            }
        })
    }

    private fun updateMarkers(list: List<Restaurant>) {
        if (::naverMap.isInitialized.not()) {
            showToastMessage("지도를 불러오는 동안 문제가 발생하였습니다.")
            return
        }

        markers.forEach { marker -> marker.map = null }
        markers = list.map { restaurant ->
            Marker(restaurant.latLng).apply {
                captionText = HtmlCompat.fromHtml(
                    restaurant.title, HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
                map = naverMap
            }
        }

        moveCamera(markers.first().position, 13.0)
    }

    private fun moveCamera(position: LatLng, zoom: Double) {
        val cameraUpdate = CameraUpdate.scrollAndZoomTo(position, zoom)
            .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
    }
}