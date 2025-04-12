package com.tesisforero.motosecure.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGoogleClient {

    private const val BASE_URL = "https://routes.googleapis.com/"

    fun getClient(apiKey: String): GoogleMapsApiService {

        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val originalUrl = original.url

            val urlWithKey = originalUrl.newBuilder()
                .addQueryParameter("key", apiKey)
                .build()

            val requestBuilder = original.newBuilder().url(urlWithKey)
            val request = requestBuilder.build()

            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleMapsApiService::class.java)
    }
}
