package org.sjhstudio.fastcampus.part1.chapter9

import android.app.*
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.os.IBinder

class MediaPlayerService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private val receiver: LowBatteryReceiver by lazy { LowBatteryReceiver() }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        initReceiver()
        startForeground(100, createNotification())
    }

    override fun onDestroy() {
        mediaPlayerStop()
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            MEDIA_PLAYER_PLAY -> mediaPlayerPlay()
            MEDIA_PLAYER_PAUSE -> mediaPlayerPause()
            MEDIA_PLAYER_STOP -> mediaPlayerStop()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun initReceiver() {
        val filter = IntentFilter()
            .apply { addAction(Intent.ACTION_BATTERY_LOW) }
        registerReceiver(receiver, filter)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            MEDIA_PLAYER_CHANNEL_ID,
            "mediaPlayerChannel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        createNotificationChannel()

        val mainPendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java)
                .apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP },
            PendingIntent.FLAG_IMMUTABLE
        )
        val pausePendingIntent = PendingIntent.getService(
            this,
            0,
            Intent(this, MediaPlayerService::class.java)
                .apply { action = MEDIA_PLAYER_PAUSE },
            PendingIntent.FLAG_IMMUTABLE
        )
        val playPendingIntent = PendingIntent.getService(
            this,
            0,
            Intent(this, MediaPlayerService::class.java)
                .apply { action = MEDIA_PLAYER_PLAY },
            PendingIntent.FLAG_IMMUTABLE
        )
        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            Intent(this, MediaPlayerService::class.java)
                .apply { action = MEDIA_PLAYER_STOP },
            PendingIntent.FLAG_IMMUTABLE
        )

        return Notification.Builder(this, MEDIA_PLAYER_CHANNEL_ID)
            .apply {
                style = Notification.MediaStyle().setShowActionsInCompactView(0, 1, 2)
                setVisibility(Notification.VISIBILITY_PUBLIC)
                setSmallIcon(R.drawable.ic_launcher_foreground)
                addAction(
                    Notification.Action.Builder(
                        Icon.createWithResource(baseContext, R.drawable.ic_pause_24),
                        "중지",
                        pausePendingIntent
                    ).build()
                )
                addAction(
                    Notification.Action.Builder(
                        Icon.createWithResource(baseContext, R.drawable.ic_play_arrow_24),
                        "시작",
                        playPendingIntent
                    ).build()
                )
                addAction(
                    Notification.Action.Builder(
                        Icon.createWithResource(baseContext, R.drawable.ic_stop_24),
                        "정지",
                        stopPendingIntent
                    ).build()
                )
                setContentTitle("음악 재생")
                setContentText("봇치더락 12화 OST - 별자리가 될 수 있다면")
                setContentIntent(mainPendingIntent)
            }.build()
    }

    private fun mediaPlayerPlay() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bocchi)
                .apply { isLooping = true }
        }
        mediaPlayer?.start()
    }

    private fun mediaPlayerPause() {
        mediaPlayer?.pause()
    }

    private fun mediaPlayerStop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        stopSelf()
    }
}