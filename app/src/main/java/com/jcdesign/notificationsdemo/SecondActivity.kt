package com.jcdesign.notificationsdemo


import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import java.lang.reflect.Array.getChar

class SecondActivity : AppCompatActivity() {

    private lateinit var result_text_view: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        result_text_view = findViewById(R.id.result_text_view)

        receiveInput()
    }

    private fun receiveInput(){
        val KEY_REPLY = "key_reply"
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if(remoteInput!=null){
            val inputString = remoteInput.getCharSequence(KEY_REPLY).toString()
            result_text_view.text = inputString
        }

        val channelId = "com.jcdesign.notificationsdemo.channel1"
        val notificationId = 45

        val repliedNotification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentText("Your reply received")
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, repliedNotification)
    }


}