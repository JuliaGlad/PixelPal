package myapplication.android.pixelpal.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import myapplication.android.pixelpal.app.AuthQueryInterceptor
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepositoryImpl
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepositoryImpl
import myapplication.android.pixelpal.data.repository.genres.GenresRepositoryImpl
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepositoryImpl
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepositoryImpl
import myapplication.android.pixelpal.data.repository.stores.StoresRepositoryImpl
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSource
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.genres.GenresLocalSource
import myapplication.android.pixelpal.data.source.genres.GenresRemoteSource
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSource
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSource
import myapplication.android.pixelpal.data.source.pulisher.PublishersLocalSource
import myapplication.android.pixelpal.data.source.pulisher.PublishersRemoteSource
import myapplication.android.pixelpal.data.source.stores.StoresLocalSource
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSource
import myapplication.android.pixelpal.domain.usecase.games.GetGamesReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import myapplication.android.pixelpal.ui.home.mvi.HomeActor
import myapplication.android.pixelpal.ui.home.mvi.HomeReducer
import myapplication.android.pixelpal.ui.home.mvi.HomeStoreFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object DiContainer {

    private val authClient = OkHttpClient.Builder().apply {
        addInterceptor(AuthQueryInterceptor())
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()

    private val jsonSerializer = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder().apply {
        baseUrl(Constants.BASE_URL)
        addConverterFactory(
            jsonSerializer.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            )
        )
        client(authClient)
    }.build()
    private val gamesApi : GamesApi = retrofit.create()

    val homeStoreFactory by lazyNone { HomeStoreFactory(homeReducer, homeActor) }

    private val homeReducer by lazyNone { HomeReducer() }

    private val homeActor by lazyNone { HomeActor(getTopGamesUseCase, getGamesReleasesUseCase) }

    private val gamesRepository by lazyNone { GamesRepositoryImpl(gamesLocalSource, gamesRemoteSource) }

    val creatorsRepository by lazyNone { CreatorsRepositoryImpl(creatorsLocalSource, creatorsRemoteSource) }

    private val genresRepository by lazyNone { GenresRepositoryImpl(genresLocalSource, genresRemoteSource) }

    private val platformRepository by lazyNone { PlatformsRepositoryImpl(platformLocalSource, platformRemoteSource) }

    private val publishersRepository by lazyNone { PublishersRepositoryImpl(publishersLocalSource, publishersRemoteSource) }

    private val storesRepository by lazyNone { StoresRepositoryImpl(storesLocalSource, storesRemoteSource) }

    private val storesRemoteSource by lazyNone { StoresRemoteSource(gamesApi) }

    private val storesLocalSource by lazyNone { StoresLocalSource() }

    private val publishersRemoteSource by lazyNone { PublishersRemoteSource(gamesApi) }

    private val publishersLocalSource by lazy { PublishersLocalSource() }

    private val platformRemoteSource by lazyNone { PlatformRemoteSource(gamesApi) }

    private val platformLocalSource by lazyNone { PlatformLocalSource() }

    private val genresRemoteSource by lazyNone { GenresRemoteSource(gamesApi) }

    private val genresLocalSource by lazyNone { GenresLocalSource() }

    private val creatorsRemoteSource by lazyNone { CreatorsRemoteSource(gamesApi) }

    private val creatorsLocalSource by  lazyNone { CreatorsLocalSource() }

    private val gamesRemoteSource by lazyNone { GamesRemoteSource(gamesApi) }

    private val gamesLocalSource by lazyNone { GamesLocalSource() }

    private val getTopGamesUseCase by lazyNone { GetTopGamesUseCase(gamesRepository) }

    private val getGamesReleasesUseCase by lazyNone { GetGamesReleasesUseCase(gamesRepository) }
}

private fun <T> lazyNone(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)