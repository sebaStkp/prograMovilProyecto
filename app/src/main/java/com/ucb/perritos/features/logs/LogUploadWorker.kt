//package com.ucb.perritos.features.logs
//
//import android.content.Context
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.calyrsoft.ucbp1.features.movie.domain.usecase.FetchPopularMoviesUseCase
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
//
//class LogUploadWorker(
//    appContext: Context,
//    workerParameters: WorkerParameters
//) : CoroutineWorker(appContext, workerParameters) , KoinComponent {
//
//    private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase by inject()
//
//    override suspend fun doWork(): Result {
//        //ejecutar instrucción para subir datos
//        println("ejecutar instrucción para subir datos")
//        val response = fetchPopularMoviesUseCase.invoke()
//        response.fold(
//            onFailure = {
//                return Result.failure()
//            },
//            onSuccess = {
//                println("datos subidos ${it.size}")
//                return Result.success()
//            }
//        )
//
//    }
//}