package com.android_labs.androidstudiotutorial

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class CustomerWork(var context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {

        Log.d("CustomerWork", "1")

        delay(1000)

//        WorkManager.getInstance(this.context).enqueue(
//            OneTimeWorkRequest.Builder(CustomerWork::class.java)
//                .setInitialDelay(1, TimeUnit.SECONDS)
//                .build()
//        )

        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }
}