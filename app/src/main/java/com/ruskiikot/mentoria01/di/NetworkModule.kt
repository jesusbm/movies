package com.ruskiikot.mentoria01.di

import com.ruskiikot.mentoria01.network.OmdbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(FragmentComponent::class)
class NetworkModule {

    private val URL_BASE = "https://www.omdbapi.com"
    private val API_KEY_NAME = "apikey"
    private val API_KEY_VALUE = "d6bc9721"

    @Provides
    fun provideOmdbApiInstance(retrofit: Retrofit): OmdbApi {
        return retrofit.create(OmdbApi::class.java)
    }

    @Provides
    fun provideRetrofitInstance(
        converterFactory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_BASE)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val urlWithKey = chain.request().url().newBuilder().addQueryParameter(
                        API_KEY_NAME,
                        API_KEY_VALUE
                    ).build()
                    val requestWithKey = chain.request().newBuilder().url(urlWithKey).build()
                    chain.proceed(requestWithKey)
                }
            )
            readTimeout(0, TimeUnit.SECONDS)
            connectTimeout(0, TimeUnit.SECONDS)
        }.build()
    }
}
