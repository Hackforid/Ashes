package com.smilehacker.ashes.foreground

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.RemoteViews
import com.smilehacker.ashes.R

/**
 * Created by zhouquan on 16/5/23.
 */
class FakeService : Service() {

    companion object {
        fun getFakeNotification(context: Context) : Notification {
            val remoteView = RemoteViews(context.packageName, R.layout.empty_notification)
            return NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_empty_notification)
                    .setContent(remoteView)
                    .build()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            startForeground(R.id.notification, getFakeNotification(this))
        } catch (e : Throwable) {
            Log.i("Ashes", "start fake service foreground fail", e)
        }
        stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        try {
            stopForeground(true)
        } catch (e : Throwable) {
            Log.i("Ashes", "stop fake service foreground fail", e)
        }
        super.onDestroy()
    }


}
