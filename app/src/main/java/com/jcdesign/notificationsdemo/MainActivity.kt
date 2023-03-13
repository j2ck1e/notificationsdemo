package com.jcdesign.notificationsdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private val channelId = "com.jcdesign.notificationsdemo.channel1"
    private var notificationManager: NotificationManager? = null
    private val KEY_REPLY = "key_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelId, "DemoChannel", "This is a demo")

        button = findViewById(R.id.button)
        button.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {
        val notificationId = 45
        val tapResultIntent = Intent(this, SecondActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // details action button
        val intentDetails = Intent(this, DetailsActivity::class.java)
        val pendingIntentDetails = PendingIntent.getActivity(
            this,
            0,
            intentDetails,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val actionDetails = NotificationCompat.Action.Builder(
            0,
            "Details",
            pendingIntentDetails
        ).build()

        // settings action button
        val intentSettings = Intent(this, SettingActivity::class.java)
        val pendingIntentSettings = PendingIntent.getActivity(
            this,
            0,
            intentSettings,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val actionSettings = NotificationCompat.Action.Builder(
            0,
            "Settings",
            pendingIntentSettings
        ).build()

        // replay action
        val remoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert your name here")
            build()
        }

        val replyAction = NotificationCompat.Action.Builder(
            0,
            "REPLY",
            pendingIntent).addRemoteInput(remoteInput).build()

        val notification = NotificationCompat.Builder(this@MainActivity, channelId)
            .setContentTitle("Demo title")
            .setContentText("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(actionDetails)
            .addAction(actionSettings)
            .addAction(replyAction)
            .build()
        notificationManager?.notify(notificationId, notification)
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val impotance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, impotance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }


}