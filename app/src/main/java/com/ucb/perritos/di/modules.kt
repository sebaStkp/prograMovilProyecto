package com.ucb.perritos.di



//import okhttp3.OkHttpClient
//import org.koin.android.BuildConfig
//import org.koin.android.ext.koin.androidApplication
//import org.koin.android.ext.koin.androidContext
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.core.qualifier.named
import com.ucb.perritos.features.bienvenida.domain.usecase.IrInicioSesionUseCase
import com.ucb.perritos.features.bienvenida.presentation.BienvenidaViewModel
import com.ucb.perritos.features.login.data.datasource.LoginDataStore
import com.ucb.perritos.features.login.data.repository.LoginRepository
import com.ucb.perritos.features.login.domain.repository.ILoginRepository
import com.ucb.perritos.features.login.domain.usecase.SetTokenUseCase
import com.ucb.perritos.features.login.presentation.LoginViewModel
import com.ucb.perritos.navigation.NavigationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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
    factory { IrInicioSesionUseCase(get()) }
    viewModel{ BienvenidaViewModel(get()) }
}