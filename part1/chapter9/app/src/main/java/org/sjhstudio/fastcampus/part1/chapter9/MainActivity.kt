package org.sjhstudio.fastcampus.part1.chapter9

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sjhstudio.fastcampus.part1.chapter9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivPlay.setOnClickListener { mediaPlayerPlay() }
        binding.ivPause.setOnClickListener { mediaPlayerPause() }
        binding.ivStop.setOnClickListener { mediaPlayerStop() }
    }

    override fun onDestroy() {
        stopService(Intent(this, MediaPlayerService::class.java))
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_POST_NOTIFICATIONS -> {
                if (grantResults.firstOrNull() != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(
                        binding.root,
                        "음악을 재생하기 위해서 알림 권한이 필요합니다.",
                        Snackbar.LENGTH_SHORT
                    ).apply {
                        setAction("허용하기") {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .apply { data = Uri.parse("package:$packageName") }
                            startActivity(intent)
                        }
                    }.show()
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        return when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> true
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> true
            shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
                Log.d("permission", "need rationale")
                AlertDialog.Builder(this)
                    .apply {
                        setMessage("음악 재생을 위해 알림 권한이 필요합니다.")
                        setPositiveButton("허용") { _, _ ->
                            requestPermissions(
                                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                                REQUEST_POST_NOTIFICATIONS
                            )
                        }
                        setNegativeButton("취소", null)
                    }.show()
                false
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_POST_NOTIFICATIONS
                )
                false
            }
        }
    }

    private fun mediaPlayerPlay() {
        if (checkPermission().not()) return

        val intent = Intent(this, MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_PLAY }
        startService(intent)
    }

    private fun mediaPlayerPause() {
        if (checkPermission().not()) return

        val intent = Intent(this, MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_PAUSE }
        startService(intent)
    }

    private fun mediaPlayerStop() {
        if (checkPermission().not()) return

        val intent = Intent(this, MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_STOP }
        startService(intent)
    }

    companion object {

        const val REQUEST_POST_NOTIFICATIONS = 100
    }
}