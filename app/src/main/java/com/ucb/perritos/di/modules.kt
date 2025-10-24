package com.ucb.perritos.di



//import okhttp3.OkHttpClient
//import org.koin.android.BuildConfig
//import org.koin.android.ext.koin.androidApplication
//import org.koin.android.ext.koin.androidContext
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.core.qualifier.named

import com.ucb.perritos.appRoomDataBase.AppRoomDataBase

import com.ucb.perritos.features.bienvenida.presentation.BienvenidaViewModel
import com.ucb.perritos.features.login.data.datasource.LoginDataStore
import com.ucb.perritos.features.login.data.repository.LoginRepository
import com.ucb.perritos.features.login.domain.repository.ILoginRepository
import com.ucb.perritos.features.login.domain.usecase.SetTokenUseCase
import com.ucb.perritos.features.login.presentation.LoginViewModel
import com.ucb.perritos.features.registroMascota.data.datasource.RegistroPerroLocalDataSource
import com.ucb.perritos.features.registroMascota.data.repository.RegistroPerroRepository
import com.ucb.perritos.features.registroMascota.domain.repository.IRegistroPerroRepository
import com.ucb.perritos.features.registroMascota.domain.usecase.RegistrarPerroUseCase
import com.ucb.perritos.features.registroMascota.presentation.RegistroPerroViewModel
import com.ucb.perritos.features.registroUsuario.data.datasource.RegistroUsuarioLocalDataSource
import com.ucb.perritos.features.registroUsuario.data.repository.RegistroUsuarioRepository
import com.ucb.perritos.features.registroUsuario.domain.repository.IRegistroUsuarioRepository
import com.ucb.perritos.features.registroUsuario.domain.usecase.RegistrarUsuarioUseCase
import com.ucb.perritos.features.registroUsuario.presentation.RegistroUsuarioViewModel
import com.ucb.perritos.navigation.NavigationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
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


//    // OkHttpClient
//    single {
//        OkHttpClient.Builder()
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .build()
//    }
//
//    // Retrofit
//    single(named(NetworkConstants.RETROFIT_GITHUB)) {
//        Retrofit.Builder()
//            .baseUrl(NetworkConstants.GITHUB_BASE_URL)
//            .client(get())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    // Retrofit
//    single(named(NetworkConstants.RETROFIT_MOVIE)) {
//        Retrofit.Builder()
//            .baseUrl(NetworkConstants.MOVIE_BASE_URL)
//            .client(get())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    // GithubService
//    single<GithubService> {
//        get<Retrofit>( named(NetworkConstants.RETROFIT_GITHUB)).create(GithubService::class.java)
//    }
//    single{ GithubRemoteDataSource(get()) }
//    single<IGithubRepository>{ GithubRepository(get()) }
//
//    factory { FindByNickNameUseCase(get()) }
//    viewModel { GithubViewModel(get(), get()) }
//
//    single<IProfileRepository> { ProfileRepository() }
//    factory { GetProfileUseCase(get()) }
//    viewModel { ProfileViewModel(get()) }
//
//    single { AppRoomDatabase.getDatabase(get()) }
//    single(named("dollarDao")) { get<AppRoomDatabase>().dollarDao() }
//    single { RealTimeRemoteDataSource() }
//    single { DollarLocalDataSource(get(named("dollarDao"))) }
//    single<IDollarRepository> { DollarRepository(get(), get()) }
//    factory { FetchDollarUseCase(get()) }
//    factory { FetchDollarParallelUseCase(get()) }
//    viewModel{ DollarViewModel(get(), get()) }
//
//
//    single(named("apiKey")) {
//        androidApplication().getString(R.string.api_key)
//    }
//
//    single<MovieService> {
//        get<Retrofit>(named(NetworkConstants.RETROFIT_MOVIE)).create(MovieService::class.java)
//    }
//    single(named("movieDao")) { get<AppRoomDatabase>().movieDao() }
//    single { MovieRemoteDataSource(get(), get(named("apiKey"))) }
//    single { MovieLocalDataSource(get(named("movieDao"))) }
//    single<IMoviesRepository> { MovieRepository(get(), get()) }
//    factory { FetchPopularMoviesUseCase(get()) }
//    factory { RateMovieUseCase(get()) }
//    viewModel{ PopularMoviesViewModel(get(), get()) }
//
//    viewModel { NavigationViewModel() }

    single { LoginDataStore(androidContext()) }
    single<ILoginRepository> { LoginRepository(get()) }
    factory { SetTokenUseCase(get()) }
    viewModel{ LoginViewModel(get()) }


    viewModel { NavigationViewModel() }
    viewModel{ BienvenidaViewModel() }


    single { AppRoomDataBase.getDatabase((get())) }
    single(named("registroPerroDao")) { get<AppRoomDataBase>().registroPerroDao() }
    single { RegistroPerroLocalDataSource(get(named("registroPerroDao"))) }
    single<IRegistroPerroRepository> { RegistroPerroRepository(get()) }
    //single(named("perfilPerroDao")) { get<AppRoomDataBase>().perfilPerroDao() }
    factory { RegistrarPerroUseCase(get()) }
    viewModel{ RegistroPerroViewModel(get()) }


    single(named("registroUsuarioDao")) { get<AppRoomDataBase>().registroUsuarioDao() }
    single { RegistroUsuarioLocalDataSource(get(named("registroUsuarioDao"))) }
    single<IRegistroUsuarioRepository> { RegistroUsuarioRepository(get()) }
    factory { RegistrarUsuarioUseCase(get()) }
    viewModel{ RegistroUsuarioViewModel(get()) }

    // DAO
    single(named("perfilPerroDao")) { get<AppRoomDataBase>().perfilPerroDao() }

// DataSource + Repo
    single { com.ucb.perritos.features.perfilPerro.data.datasource.PerfilPerroLocalDataSource(get(named("perfilPerroDao"))) }
    single<com.ucb.perritos.features.perfilPerro.domain.repository.IPerfilPerroRepository> {
        com.ucb.perritos.features.perfilPerro.data.repository.PerfilPerroRepository(get())
    }
// Use case
    factory { com.ucb.perritos.features.perfilPerro.domain.usecase.ObtenerPerfilPerroUseCase(get()) }
// ViewModel
    viewModel { com.ucb.perritos.features.perfilPerro.presentation.PerfilPerroViewModel(get()) }
}