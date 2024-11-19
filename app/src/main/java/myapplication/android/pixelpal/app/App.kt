package myapplication.android.pixelpal.app

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import myapplication.android.pixelpal.data.api.GamesApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

internal class App : Application() {
    private lateinit var gamesApi: GamesApi
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        createClient()
    }

    private fun createClient() {
        val authClient = OkHttpClient.Builder().apply {
            addInterceptor(AuthQueryInterceptor())
        }.build()

        val jsonSerializer = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder().apply {
            baseUrl(Constants.BASE_URL)
            addConverterFactory(
                jsonSerializer.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            client(authClient)
        }.build()
        gamesApi = retrofit.create()
    }

    companion object {
        internal lateinit var INSTANCE: App
            private set
    }

}