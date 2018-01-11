package com.smilehacker.ashes.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

/**
 * Created by kleist on 16/5/24.
 */
class JobSchedulerService : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.i(JobSchedulerService::class.java.simpleName, "on Start job")
        //startService<AshesService>()
        return false
    }
}
