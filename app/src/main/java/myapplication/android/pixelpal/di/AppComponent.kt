package myapplication.android.pixelpal.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import myapplication.android.pixelpal.data.api.GamesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitModule::class
    ]
)
interface AppComponent {

    fun httpClient(): OkHttpClient

    fun videoRetrofit(): Retrofit

    fun provideApi(): GamesApi

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}
