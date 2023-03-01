package org.sjhstudio.fastcampus.part1.chapter8.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.sjhstudio.fastcampus.part1.chapter8.R
import org.sjhstudio.fastcampus.part1.chapter8.data.ImageItem
import org.sjhstudio.fastcampus.part1.chapter8.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part1.chapter8.ui.adapter.ImageAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val imageAdapter: ImageAdapter by lazy {
        ImageAdapter {
            checkPermission()
        }
    }
    private val imageLoadLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            updateImages(uriList)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_image -> {
                checkPermission()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                val grantResult = grantResults.firstOrNull() ?: PackageManager.PERMISSION_DENIED
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    loadImage()
                } else {
                    Snackbar.make(binding.root, "외부 저장소 읽기 권한이 거절되었습니다.", Snackbar.LENGTH_SHORT)
                        .apply {
                            setAction("허용") {
                                val intent =
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                        data = Uri.parse("package:$packageName")
                                    }
                                startActivity(intent)
                            }
                        }.show()
                }
            }
        }
    }

    private fun initView() {
        with(binding) {
            toolbar.apply {
                title = "사진 가져오기"
                setSupportActionBar(this)
            }
            rvImage.apply {
                adapter = imageAdapter
                layoutManager = GridLayoutManager(this@MainActivity, 2)
            }
            btnNavigateFrameActivity.setOnClickListener {
                navigateToFrameActivity()
            }
        }
    }

    private fun checkPermission() {
        when {
            checkSelfPermission(imagePermission) == PackageManager.PERMISSION_GRANTED -> {
                loadImage()
            }
            shouldShowRequestPermissionRationale(imagePermission) -> {
                showPermissionRationaleDialog()
            }
            else -> {
                requestPermissions(arrayOf(imagePermission), REQUEST_READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("이미지를 가져오기 위해서\n외부 저장소 읽기 권한이 필요합니다.")
            setNegativeButton("취소", null)
            setPositiveButton("동의") { _, _ ->
                requestPermissions(arrayOf(imagePermission), REQUEST_READ_EXTERNAL_STORAGE)
            }
        }.show()
    }

    private fun loadImage() {
        imageLoadLauncher.launch("image/*")
    }

    private fun updateImages(uriList: List<Uri>) {
        Log.i("updateImages", "$uriList")
        val updateImages = imageAdapter.currentList.toMutableList()
            .apply {
                addAll(uriList.map { uri -> ImageItem.Image(uri) })
            }
        imageAdapter.submitList(updateImages)
    }

    private fun navigateToFrameActivity() {
        val images = imageAdapter.currentList.filterIsInstance<ImageItem.Image>()
            .map { it.uri.toString() }
            .toTypedArray()
        val intent = Intent(this, FrameActivity::class.java)
            .apply { putExtra("images", images) }
        startActivity(intent)
    }

    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 100
        val imagePermission =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) android.Manifest.permission.READ_MEDIA_IMAGES
            else android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
}