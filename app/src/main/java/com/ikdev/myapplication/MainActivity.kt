package com.ikdev.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import androidx.work.workDataOf
import com.ikdev.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val requst = OneTimeWorkRequestBuilder<MyWorkManager>()
            .setConstraints(constraints)
            .setInputData(workDataOf("image" to "imageUrl"))
            .build()

        binding.startBtn.setOnClickListener {
            WorkManager.getInstance(this)
                .enqueueUniqueWork("My_work", ExistingWorkPolicy.REPLACE, requst)
            WorkManager
                .getInstance(this)
                .getWorkInfoByIdLiveData(requst.id)
                .observeForever {
                    Log.d("IKPYDEV", "Progress : ${it.progress.getInt("progres",0)} ")
                    if (it.state == WorkInfo.State.SUCCEEDED) {
                        val result = it.outputData.getString("outdata")
                        Toast.makeText(this, "Work sescuful and $result ", Toast.LENGTH_SHORT)
                            .show()
                    } else if (it.state == WorkInfo.State.FAILED) {
                        Toast.makeText(this, "Work Failed", Toast.LENGTH_SHORT).show()

                    }
                }

        }


    }
}