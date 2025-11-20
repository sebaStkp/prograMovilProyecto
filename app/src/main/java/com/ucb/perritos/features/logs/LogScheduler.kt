//package com.ucb.perritos.features.logs
//
//import android.content.Context
//import androidx.work.Constraints
//import androidx.work.ExistingPeriodicWorkPolicy
//import androidx.work.NetworkType
//import androidx.work.PeriodicWorkRequest
//import androidx.work.WorkManager
//import java.util.concurrent.TimeUnit
//
//class LogScheduler(
//    private val context: Context
//) {
//    private val LOG_WORKNAME = "logUploadWork"
//    private val INTERVAL_MINUTES = 15L
//
//    fun schedulePeriodicaUpload() {
//        val logRequest = PeriodicWorkRequest.Builder(
//            LogUploadWorker::class.java,
//            INTERVAL_MINUTES,
//            TimeUnit.MINUTES
//        )
//            .setConstraints(
//                Constraints.Builder()
//                    .setRequiredNetworkType(NetworkType.CONNECTED)
//                    .build()
//            ).build()
//
//        WorkManager.getInstance(context.applicationContext).enqueueUniquePeriodicWork(
//            LOG_WORKNAME,
//            ExistingPeriodicWorkPolicy.KEEP,
//            logRequest
//        )
//    }
//}