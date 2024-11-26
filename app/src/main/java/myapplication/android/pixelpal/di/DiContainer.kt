package myapplication.android.pixelpal.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import myapplication.android.pixelpal.app.AuthQueryInterceptor
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepositoryImpl
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
import myapplication.android.pixelpal.domain.usecase.creators.GetCreatorsUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesReleasesUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetGamesShortDataUseCase
import myapplication.android.pixelpal.domain.usecase.games.GetTopGamesUseCase
import myapplication.android.pixelpal.domain.usecase.genres.GetGenreDescriptionUseCase
import myapplication.android.pixelpal.domain.usecase.genres.GetGenresUseCase
import myapplication.android.pixelpal.domain.usecase.platofrms.GetPlatformsUseCase
import myapplication.android.pixelpal.domain.usecase.publishers.GetPublishersUseCase
import myapplication.android.pixelpal.domain.usecase.stores.GetStoresUseCase
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsActor
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsReducer
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsStoreFactory
import myapplication.android.pixelpal.ui.games.games.mvi.GamesActor
import myapplication.android.pixelpal.ui.games.games.mvi.GamesReducer
import myapplication.android.pixelpal.ui.games.games.mvi.GamesStoreFactory
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesActor
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesReducer
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesStoreFactory
import myapplication.android.pixelpal.ui.home.mvi.HomeActor
import myapplication.android.pixelpal.ui.home.mvi.HomeReducer
import myapplication.android.pixelpal.ui.home.mvi.HomeStoreFactory
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformActor
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformReducer
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformStoreFactory
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresActor
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresReducer
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresStoreFactory
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

    val creatorsStoreFactory by lazyNone { CreatorsStoreFactory(creatorsReducer, creatorActor) }

    val homeStoreFactory by lazyNone { HomeStoreFactory(homeReducer, homeActor) }

    val storesStoreFactory by lazyNone { StoresStoreFactory(storesReducer, storeActor) }

    val mainGamesStoreFactory by lazyNone { MainGamesStoreFactory(mainGamesReducer, mainGamesActor) }

    val platformStoreFactory by lazyNone { PlatformStoreFactory(platformActor, platformReducer) }

    val gamesStoreFactory by lazyNone { GamesStoreFactory(gamesReducer, gamesActor) }

    private val gamesReducer by lazyNone { GamesReducer() }

    private val gamesActor by lazyNone { GamesActor(getGamesShortDataUseCase) }

    private val platformReducer by lazyNone { PlatformReducer() }

    private val mainGamesActor by lazyNone { MainGamesActor(getGenresUseCase) }

    private val mainGamesReducer by lazyNone { MainGamesReducer() }

    private val platformActor by lazyNone { PlatformActor(getPlatformsUseCase) }

    private val storesReducer by lazyNone { StoresReducer() }

    private val storeActor by lazyNone { StoresActor(getStoresUseCase) }

    private val homeReducer by lazyNone { HomeReducer() }

    private val homeActor by lazyNone { HomeActor(getTopGamesUseCase, getGamesReleasesUseCase) }

    private val creatorsReducer by lazyNone { CreatorsReducer() }

    private val creatorActor by lazyNone { CreatorsActor(getCreatorsUseCase, getPublishersUseCase) }

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

    private val getCreatorsUseCase by lazyNone { GetCreatorsUseCase(creatorsRepository) }

    private val getStoresUseCase by lazyNone { GetStoresUseCase(storesRepository) }

    private val getPlatformsUseCase by lazyNone { GetPlatformsUseCase(platformRepository) }

    private val getTopGamesUseCase by lazyNone { GetTopGamesUseCase(gamesRepository) }

    private val getPublishersUseCase by lazyNone { GetPublishersUseCase(publishersRepository) }

    private val getGamesReleasesUseCase by lazyNone { GetGamesReleasesUseCase(gamesRepository) }

    val getGenresDescriptionUseCase by lazyNone { GetGenreDescriptionUseCase(genresRepository) }

    val getGamesShortDataByGenreUseCase by lazyNone { GetGamesShortDataUseCase(gamesRepository) }

    private val getGenresUseCase by lazyNone { GetGenresUseCase(genresRepository) }

    private val getGamesShortDataUseCase by lazyNone { GetGamesShortDataUseCase(gamesRepository) }
}

private fun <T> lazyNone(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)