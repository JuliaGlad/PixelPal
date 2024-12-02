package myapplication.android.pixelpal.di

import com.google.android.gms.common.api.Api
import com.google.gson.JsonSerializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.serialization.json.Json
import myapplication.android.pixelpal.app.AuthQueryInterceptor
import myapplication.android.pixelpal.data.api.GamesApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class RetrofitModule {

    val jsonSerializer = Json { ignoreUnknownKeys = true }

    @Reusable
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(AuthQueryInterceptor())
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()

    @Reusable
    @Provides
    fun provideGamesRetrofit(authClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(
                jsonSerializer.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            client(authClient)
        }.build()

    @Singleton
    @Provides
    fun provideGamesApi(retrofit: Retrofit): GamesApi =
        retrofit.create(GamesApi::class.java)

    companion object {
        const val API_KEY = "e2b544813c594d0a8e3d65dca3fd132f"
        const val BASE_URL = "https://api.rawg.io/api/"
    }
}