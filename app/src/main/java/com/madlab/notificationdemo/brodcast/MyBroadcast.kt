package com.madlab.notificationdemo.brodcast

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.madlab.notificationdemo.ConstantResource

class MyBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val remoteInput = RemoteInput.getResultsFromIntent(intent)

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (intent!!.getIntExtra("id", -1) == 1) {
            notificationManager.cancel(intent.extras?.get("id") as Int)
            Toast.makeText(context, "Notification Cancel", Toast.LENGTH_SHORT).show()
        }

        if (intent.getIntExtra(ConstantResource.KEY_DATA, -1) == 99) {

            if (remoteInput != null) {
                val mes = remoteInput.getCharSequence(ConstantResource.NOTIFICATION_REPLY)
                Toast.makeText(context, mes, Toast.LENGTH_LONG).show()
                notificationManager.cancel(intent.extras?.get(ConstantResource.KEY_DATA) as Int)
            }
        }

        if (intent.getIntExtra(
                ConstantResource.KEY_HELP,
                -1
            ) == ConstantResource.REQUEST_CODE_HELP
        ) {
            Toast.makeText(context, "You Clicked Help", Toast.LENGTH_SHORT).show()
        }

        if (intent.getIntExtra(ConstantResource.KEY_MORE, -1) == ConstantResource.REQUEST_CODE_MORE){

        }
            Toast.makeText(context, "You Clicked More", Toast.LENGTH_SHORT).show()
    }
}