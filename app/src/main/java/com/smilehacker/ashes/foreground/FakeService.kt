package com.smilehacker.ashes.foreground

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.smilehacker.ashes.R

/**
 * Created by zhouquan on 16/5/23.
 */
class FakeService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(R.id.notification, Notification())
        stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
    }


}
