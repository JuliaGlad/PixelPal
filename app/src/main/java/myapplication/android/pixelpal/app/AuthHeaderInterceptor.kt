package myapplication.android.pixelpal.app

import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithHeader = chain
            .request()
            .newBuilder()
            .addHeader("x-api-key", Constants.API_KEY)
            .build()
        return chain.proceed(requestWithHeader)
    }
}