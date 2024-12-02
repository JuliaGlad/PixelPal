package myapplication.android.pixelpal.app

import myapplication.android.pixelpal.di.RetrofitModule.Companion.API_KEY
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AuthQueryInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url: HttpUrl = request
            .url
            .newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}