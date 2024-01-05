package com.compose.movie.di

import com.compose.movie.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderNetworkInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val requestBuilder = original.newBuilder().addHeader(
            "Authorization",
            getAPIToken(),
        ).addHeader("Accept", "application/json")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun getAPIToken() = BuildConfig.ACCESS_TOKEN
}
