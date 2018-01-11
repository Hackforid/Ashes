package com.smilehacker.ashes.foreground

import android.accounts.Account
import android.accounts.AccountManager
import android.annotation.TargetApi
import android.app.Notification
import android.app.Service
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.smilehacker.ashes.R
import com.smilehacker.ashes.jobscheduler.JobSchedulerService
import org.jetbrains.anko.startService

/**
 * Created by zhouquan on 16/5/23.
 */
class AshesService : Service() {

    private val JOBSCHEDULER_ID = 24543
    private val JOB_SYNC_PERIODIC = 5 // min

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AshesService::class.java)
            context.startService(intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        doFake()
        startAccountSync()
        startJobScheduler()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    private fun doFake() {
        Log.i(AshesService::class.java.simpleName, "start fake")
        try {
            if (Build.VERSION.SDK_INT<= 17) {
                startForeground(R.id.notification, Notification())
            } else {
                startForeground(R.id.notification, Notification())
                startService<FakeService>()
            }
        } catch (e: Throwable) {
            Log.e("Ashes", "do fake fail", e)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startJobScheduler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val builder = JobInfo.Builder(JOBSCHEDULER_ID, ComponentName(packageName, JobSchedulerService::class.java.name))
            builder
                    .setPeriodic((60 * 1000 * JOB_SYNC_PERIODIC).toLong())
                    // need RECEIVE_BOOT_COMPLETED permission.
                    .setPersisted(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setRequiresCharging(false)
                    .setRequiresDeviceIdle(false)
            jobScheduler.schedule(builder.build())
        }
    }

    private fun startAccountSync() {
        try {
            val accountManager = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
            val accountType = getString(R.string.account_type)
            val accountName = getString(R.string.app_name)

            var accounts = accountManager.getAccountsByType(accountType)
            var account = accounts.find { it.name == accountName }
            if (account == null) {
                account = Account(accountName, getString(R.string.account_type))
                accountManager.addAccountExplicitly(account, "P@ssw0rd", null)
            }

            val authority = getString(R.string.account_auth_provider)

            ContentResolver.setIsSyncable(account, authority, 1)
            ContentResolver.setSyncAutomatically(account, authority, true)
            ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, 60 * 60) // 1 hour
        } catch (e : Throwable) {
        }
    }

}
