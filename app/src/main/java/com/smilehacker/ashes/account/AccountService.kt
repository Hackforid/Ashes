package com.smilehacker.ashes.account

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by zhouquan on 16/5/21.
 */
class AccountService : Service() {

    private val mAuthenticator : Authenticator by lazy { Authenticator(this) }

    override fun onBind(intent: Intent?): IBinder? {
        return mAuthenticator.iBinder
    }

    override fun onCreate() {
        super.onCreate()
    }
}