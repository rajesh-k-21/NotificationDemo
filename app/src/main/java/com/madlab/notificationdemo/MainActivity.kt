package com.madlab.notificationdemo

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val action =
            NotificationCompat.Action.Builder(
                R.drawable.ic_send_black_24dp, "Cancel", PendingIntent.getBroadcast(
                    this, 2,
                    Intent("cancel_action").putExtra("id", 1), PendingIntent.FLAG_CANCEL_CURRENT
                )
            ).build()

        buttonNotification.setOnClickListener {
            createNotification(
                this,
                "Test notification",
                "It is demo app", 1, R.drawable.ic_notifications_active_black_24dp, action
            )
        }
        buttonAddNotificationInGroup.setOnClickListener {
            addNotificationInGroup()
        }
        buttonReplayNotification.setOnClickListener {
            createReplayNotification(99)
        }
    }
}
