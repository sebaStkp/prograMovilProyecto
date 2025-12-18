package com.ucb.perritos.di



//import okhttp3.OkHttpClient
//import org.koin.android.BuildConfig
//import org.koin.android.ext.koin.androidApplication
//import org.koin.android.ext.koin.androidContext
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.core.qualifier.named

import com.ucb.perritos.appRoomDataBase.AppRoomDataBase

import com.ucb.perritos.features.bienvenida.presentation.BienvenidaViewModel
//import com.ucb.perritos.features.buscarMascota.data.datasource.BuscarPerroLocalDataSource
//import com.ucb.perritos.features.buscarMascota.data.repository.BuscarMascotaRepository
//import com.ucb.perritos.features.buscarMascota.domain.repository.IBuscarMascotaRepository
import com.ucb.perritos.features.buscarMascota.presentation.BuscarMascotaViewModel

import com.ucb.perritos.features.login.data.datasource.LoginDataStore
import com.ucb.perritos.features.login.data.repository.LoginRepository
import com.ucb.perritos.features.login.domain.repository.ILoginRepository
import com.ucb.perritos.features.login.domain.usecase.SetTokenUseCase
import com.ucb.perritos.features.login.presentation.LoginViewModel
import com.ucb.perritos.features.perfilPerro.data.datasource.PerfilPerroLocalDataSource
import com.ucb.perritos.features.perfilPerro.data.datasource.PerfilPerroRemoteSupabase
import com.ucb.perritos.features.perfilPerro.data.repository.PerfilPerroRepository
import com.ucb.perritos.features.perfilPerro.domain.repository.IPerfilPerroRepository
import com.ucb.perritos.features.perfilPerro.domain.usecase.EstablecerPerfilActualUseCase
import com.ucb.perritos.features.perfilPerro.domain.usecase.ObtenerPerfilPerroUseCase
import com.ucb.perritos.features.perfilPerro.presentation.PerfilPerroViewModel
import com.ucb.perritos.features.perfilUsuario.presentation.PerfilUsuarioViewModel


import com.ucb.perritos.features.perrosRegistrados.presentation.PerrosRegistradosViewModel
import com.ucb.perritos.features.registroMascota.data.datasource.PerroLocalDataSource
import com.ucb.perritos.features.registroMascota.data.datasource.PerroRemoteSupabase
import com.ucb.perritos.features.registroMascota.data.repository.RegistroPerroRepository
import com.ucb.perritos.features.registroMascota.domain.repository.IRegistroPerroRepository
import com.ucb.perritos.features.registroMascota.domain.usecase.EditarPerroUseCase
import com.ucb.perritos.features.registroMascota.domain.usecase.ObtenerPerrosUseCase
import com.ucb.perritos.features.registroMascota.domain.usecase.RegistrarPerroUseCase
import com.ucb.perritos.features.registroMascota.presentation.RegistroPerroViewModel
import com.ucb.perritos.features.registroUsuario.data.datasource.RegistroUsuarioLocalDataSource
import com.ucb.perritos.features.registroUsuario.data.repository.RegistroUsuarioRepository
import com.ucb.perritos.features.registroUsuario.domain.repository.IRegistroUsuarioRepository
import com.ucb.perritos.features.registroUsuario.domain.usecase.GetUserUseCase
import com.ucb.perritos.features.registroUsuario.domain.usecase.GetUsuarioActual
import com.ucb.perritos.features.registroUsuario.domain.usecase.RegistrarUsuarioUseCase
import com.ucb.perritos.features.registroUsuario.presentation.RegistroUsuarioViewModel
import com.ucb.perritos.navigation.NavigationViewModel
import io.github.jan.supabase.createSupabaseClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.ucb.perritos.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.ucb.perritos.features.buscarMascota.data.remote.UbicacionApi
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.storage.Storage
import com.ucb.perritos.features.core.SessionManagerAndroid
import com.ucb.perritos.features.registroUsuario.data.datasource.RegistroUsuarioRemoteDataSource

//import io.github.jan.supabase.gotrue.
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit

//object NetworkConstants {
//    const val RETROFIT_GITHUB = "RetrofitGithub"
//    const val GITHUB_BASE_URL = "https://api.github.com/"
//    const val RETROFIT_MOVIE = "RetrofitMovie"
//    const val MOVIE_BASE_URL = "https://api.themoviedb.org/"
//}
//
val appModule = module {


    single {
        createSupabaseClient(
            supabaseUrl = "https://efxdadwzzvsuvtefmlbm.supabase.co",
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            install(Postgrest)
            install(Storage)
            install(Auth) {
                // Aquí usamos el contexto de Android para guardar la sesión
                sessionManager = SessionManagerAndroid(androidContext())
            }
        }
    }



    single { LoginDataStore(androidContext()) }
    single<ILoginRepository> { LoginRepository(get(), get()) }
    factory { SetTokenUseCase(get()) }
    factory { GetUserUseCase(get()) }
    viewModel { LoginViewModel(get(), get (), get(), get()) }


    viewModel { NavigationViewModel() }
    viewModel { BienvenidaViewModel() }


    single { AppRoomDataBase.getDatabase((get())) }
    single(named("registroPerroDao")) { get<AppRoomDataBase>().registroPerroDao() }
    single { PerroLocalDataSource(get(named("registroPerroDao"))) }
    single { PerroRemoteSupabase(get()) }
    single<IRegistroPerroRepository> { RegistroPerroRepository(get(), get()) }
    //single(named("perfilPerroDao")) { get<AppRoomDataBase>().perfilPerroDao() }
    single { EstablecerPerfilActualUseCase(get()) }

    factory { EditarPerroUseCase(get()) }
    factory { RegistrarPerroUseCase(get()) }
    viewModel { RegistroPerroViewModel(get(),get(),get(), appContext = androidContext())}


    single(named("registroUsuarioDao")) { get<AppRoomDataBase>().registroUsuarioDao() }
    single { RegistroUsuarioLocalDataSource(get(named("registroUsuarioDao"))) }
    single { RegistroUsuarioRemoteDataSource(get()) }
    single<IRegistroUsuarioRepository> { RegistroUsuarioRepository(get(), get()) }
    factory { RegistrarUsuarioUseCase(get()) }
    viewModel { RegistroUsuarioViewModel(get()) }

    single(named("perfilPerroDao")) { get<AppRoomDataBase>().perfilPerroDao() }
    single { PerfilPerroLocalDataSource(get(named("perfilPerroDao"))) }
    single { PerfilPerroRemoteSupabase(get()) }
    single<IPerfilPerroRepository> { PerfilPerroRepository(get(),get()) }
    factory { ObtenerPerfilPerroUseCase(get()) }
    viewModel { PerfilPerroViewModel(get(), get()) }


//    factory { ObtenerUbicacionActualUseCase(get()) }
    single(named("flaskRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://simulacion-perritos.onrender.com") // emulador
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<UbicacionApi> {
        get<Retrofit>(named("flaskRetrofit")).create(UbicacionApi::class.java)
    }
    viewModel { BuscarMascotaViewModel(get()) }


    factory { ObtenerPerrosUseCase(get()) }
    viewModel { PerrosRegistradosViewModel(get(), get()) }

    factory { GetUsuarioActual(get()) }
    viewModel { PerfilUsuarioViewModel(get()) }

}

