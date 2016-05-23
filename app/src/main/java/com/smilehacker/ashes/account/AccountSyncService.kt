package com.smilehacker.ashes.account

import android.accounts.Account
import android.app.Service
import android.content.*
import android.os.Bundle
import android.os.IBinder

/**
 * Created by kleist on 16/5/23.
 */
class AccountSyncService : Service() {
    companion object {
        val syncAdapterLock = Object()
        var syncAdapter : AccountSyncAdapter? = null
    }

    override fun onBind(intent: Intent?): IBinder? {
        synchronized(syncAdapterLock) {
            if (syncAdapter == null) {
                syncAdapter = AccountSyncAdapter(applicationContext, autoInitialize = true)
            }
        }
        return syncAdapter?.syncAdapterBinder
    }
}

class AccountSyncAdapter(context: Context?, autoInitialize: Boolean) : AbstractThreadedSyncAdapter(context, autoInitialize) {

    override fun onPerformSync(account: Account?, extras: Bundle?, authority: String?, provider: ContentProviderClient?, syncResult: SyncResult?) {
        context.contentResolver.notifyChange(AccountProvider.CONTENT_URI, null, false)
    }

}
