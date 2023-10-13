package com.ikdev.myapplication

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay
import kotlin.math.log


class MyWorkManager(context:Context,parameters: WorkerParameters):CoroutineWorker(context,parameters) {
    val TAG = "IKPYDEV"
    override suspend fun doWork(): Result {
        val result = (0..100).random()
        Log.d(TAG, "doWork: ${inputData.getString("image")}")
        Log.d(TAG, "doWork: ${Thread.currentThread().name}")
        Log.d(TAG, "doWork: $result")
        repeat(100){
            setProgress(workDataOf("progres" to it+1))
            delay(100)
        }
        return if (result %2 == 0 ){
            Result.success(workDataOf("outdata" to "Photo dowland"))

        }else{
            Result.failure()
        }
    }
}