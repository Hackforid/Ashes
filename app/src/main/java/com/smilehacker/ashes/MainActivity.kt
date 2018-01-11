package com.smilehacker.ashes

import android.accounts.Account
import android.accounts.AccountManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.smilehacker.ashes.account.AccountProvider
import com.smilehacker.ashes.foreground.AshesService
import com.smilehacker.ashes.jobscheduler.JobSchedulerService
import org.jetbrains.anko.button
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startService
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accountName = "xiaoming"

        verticalLayout {
            /**
             * 利用Account的Sync拉起服务
             */
            button("Add account") {
                onClick {
                    val accountManager = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
                    val account = Account(accountName, getString(R.string.account_type))
                    accountManager.addAccountExplicitly(account, "aaa", null)
                }
            }
            button("set auto sync") {
                onClick {
                    val account = Account(accountName, getString(R.string.account_type))
                    ContentResolver.setIsSyncable(account, AccountProvider.AUTHORITY, 1)
                    ContentResolver.setSyncAutomatically(account, AccountProvider.AUTHORITY, true)
                    ContentResolver.addPeriodicSync(account, AccountProvider.AUTHORITY, Bundle.EMPTY, 60)
                }
            }
            button("Fake Service") {
                onClick {
                    startService<AshesService>()
                }
            }
            button("Job Service") {
                onClick {
                    if (Build.VERSION.SDK_INT >= 21) {
                        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                        val builder = JobInfo.Builder(1, ComponentName(packageName, JobSchedulerService::class.java.name))
                        builder
                                .setPeriodic(60 * 1000)
                                // need RECEIVE_BOOT_COMPLETED permission.
                                .setPersisted(true)
                                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                                .setRequiresCharging(false)
                                .setRequiresDeviceIdle(false)
                        jobScheduler.schedule(builder.build())
                    }
                }
            }
        }
    }

    private fun getAccount() {
        val accountManager = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
        val accounts = accountManager.getAccountsByType("com.smilehacker")

    }
}
