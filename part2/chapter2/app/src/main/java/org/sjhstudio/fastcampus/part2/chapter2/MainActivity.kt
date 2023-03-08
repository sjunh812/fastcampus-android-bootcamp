package org.sjhstudio.fastcampus.part2.chapter2

import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.sjhstudio.fastcampus.part2.chapter2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val REQUEST_RECORD_AUDIO = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnRecord.setOnClickListener { checkPermissions() }
        }
    }

    private fun checkPermissions() {
        when {
            checkSelfPermission(RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED -> {
                // todo 녹음 시작
            }
            shouldShowRequestPermissionRationale(RECORD_AUDIO) -> {
                showPermissionRationaleDialog()
            }
            else -> {
                requestPermissions(arrayOf(RECORD_AUDIO), REQUEST_RECORD_AUDIO)
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("녹음 권한을 허용해야 앱을 정상적으로 사용할 수 있습니다.")
            setPositiveButton("허용하기") { _, _ ->
                requestPermissions(
                    arrayOf(RECORD_AUDIO),
                    REQUEST_RECORD_AUDIO
                )
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun showPermissionSettingsDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("녹음 권한을 허용해야 앱을 정상적으로 사용할 수 있습니다. 앱 설정 화면에서 권한을 켜주세요.")
            setPositiveButton("변경하기") { _, _ -> navigateToAppSettings() }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun navigateToAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .apply { data = Uri.fromParts("package", packageName, null) }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val audioPermissionGranted = requestCode == REQUEST_RECORD_AUDIO
                && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (audioPermissionGranted) {
            // todo 녹음 시작
        } else if (shouldShowRequestPermissionRationale(RECORD_AUDIO)) {
            showPermissionRationaleDialog()
        } else {
            showPermissionSettingsDialog()
        }
    }
}