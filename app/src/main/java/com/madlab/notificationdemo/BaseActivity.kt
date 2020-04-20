package com.madlab.notificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.madlab.notificationdemo.brodcast.MyBroadcast


open class BaseActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: NotificationCompat.Builder
    private var channelId: String? = null

    var bundleNotificationsId: String? = null
    var bundleNotificationId = 100
    var singleNotificationId = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        channelId =
            "${applicationContext.packageName}-${applicationContext.getString(R.string.app_name)}"

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun createReplayNotification(
        id: Int
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                channelId,
                "Replay Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.WHITE
            notificationChannel.setSound(
                Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + R.raw.`when`),
                null
            )
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            val morePendingIntent = PendingIntent.getBroadcast(
                this, ConstantResource.REQUEST_CODE_MORE,
                Intent(this, MyBroadcast::class.java).putExtra(
                    ConstantResource.KEY_MORE,
                    ConstantResource.REQUEST_CODE_MORE
                ), PendingIntent.FLAG_UPDATE_CURRENT
            )

            val helpPendingIntent = PendingIntent.getBroadcast(
                this,
                ConstantResource.REQUEST_CODE_HELP,
                Intent(this, MyBroadcast::class.java).putExtra(
                    ConstantResource.KEY_HELP,
                    ConstantResource.REQUEST_CODE_HELP
                ), PendingIntent.FLAG_UPDATE_CURRENT
            )

            val dataPendingIntent = PendingIntent.getBroadcast(
                this,
                ConstantResource.REQUEST_CODE_DATA,
                Intent(this, MyBroadcast::class.java).putExtra
                    (ConstantResource.KEY_DATA, id),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val remoteInput = RemoteInput.Builder(ConstantResource.NOTIFICATION_REPLY)
                .setLabel("Enter Mes")
                .build()

            val action = NotificationCompat.Action.Builder(
                    R.drawable.ic_send_black_24dp,
                    "Replay Now",
                    dataPendingIntent
                )
                .addRemoteInput(remoteInput)
                .build()

            builder = NotificationCompat.Builder(this, channelId!!)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Replay Notification")
                .setContentText("Replay Me")
                .setAutoCancel(true)
                .addAction(action)
                .addAction(android.R.drawable.ic_menu_more, "More", morePendingIntent)
                .addAction(android.R.drawable.ic_menu_help, "Help", helpPendingIntent)

            notificationManager.notify(id, builder.build())
        }
    }

    fun createNotification(
        context: Context,
        description: String? = null,
        title: String? = null,
        id: Int,
        icon: Int = R.drawable.ic_notifications_black_24dp
    ) {

        val intent = Intent(context, AfterNotificationActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                channelId,
                description,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.WHITE
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, channelId!!)
                .setSmallIcon(icon)
                .setContentText(description)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        } else {
            builder = NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentText(description)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
        notificationManager.notify(id, builder.build())
    }

    fun createNotification(
        context: Context,
        description: String? = null,
        title: String? = null,
        id: Int,
        icon: Int = R.drawable.ic_notifications_black_24dp,
        action: NotificationCompat.Action
    ) {

        val intent = Intent(context, AfterNotificationActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                channelId,
                description,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.WHITE
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, channelId!!)
                .setSmallIcon(icon)
                .setContentText(description)
                .setContentTitle(title)
                .addAction(action)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        } else {
            builder = NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentText(description)
                .setContentTitle(title)
                .addAction(action)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
        notificationManager.notify(id, builder.build())
    }

    fun addNotificationInGroup() {

        bundleNotificationsId = "bundle_notification_$bundleNotificationId"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.notificationChannels.size < 2) {
                val groupChannel = NotificationChannel(
                    "bundle_channel_id",
                    "bundle_channel_name",
                    NotificationManager.IMPORTANCE_LOW
                )
                notificationManager.createNotificationChannel(groupChannel)
                val channel = NotificationChannel(
                    "channel_id",
                    "channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
        }
        builder = NotificationCompat.Builder(this, "bundle_channel_id")
            .setGroup(bundleNotificationsId)
            .setGroupSummary(true)
            .setContentTitle("Bundled Notification $bundleNotificationId")
            .setContentText("Content Text for group summary")
            .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)


        if (singleNotificationId == bundleNotificationId) {
            singleNotificationId =
                bundleNotificationId + 1
        } else {
            singleNotificationId++
        }

        val notification =
            NotificationCompat.Builder(this, "channel_id")
                .setGroup(bundleNotificationsId)
                .setContentTitle("New Notification $singleNotificationId")
                .setContentText("Content for the notification")
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setGroupSummary(false)

        notificationManager.notify(singleNotificationId, notification.build())
        notificationManager.notify(bundleNotificationId, builder.build())
    }
}