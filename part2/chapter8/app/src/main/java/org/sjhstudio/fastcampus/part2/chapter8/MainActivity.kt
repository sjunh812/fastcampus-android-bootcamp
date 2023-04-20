package org.sjhstudio.fastcampus.part2.chapter8

import android.accounts.NetworkErrorException
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import org.sjhstudio.fastcampus.part2.chapter8.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter8.network.exception.EmptyBodyException
import org.sjhstudio.fastcampus.part2.chapter8.network.model.SearchResult
import org.sjhstudio.fastcampus.part2.chapter8.repository.SearchRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var naverMap: NaverMap

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
        testApi()
    }

    private fun initViews() {
        with(binding) {
            searchView.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.e(LOG, query ?: "query error")
                    return false
                }

                override fun onQueryTextChange(newText: String?) = true
            })
        }
    }

    private fun testApi() {
        SearchRepository.getRestaurant("일산").enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                try {
                    val res = response.takeIf { it.isSuccessful } ?: throw NetworkErrorException()
                    val items = res.body()?.items ?: throw EmptyBodyException()
                    Log.e(LOG, "$items")
                } catch (e: NetworkErrorException) {
                    Log.e(LOG, e.toString())
                } catch (e: EmptyBodyException) {
                    Log.e(LOG, e.toString())
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
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

        // camera move example
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.666102, 126.9783881))
            .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)
    }
}