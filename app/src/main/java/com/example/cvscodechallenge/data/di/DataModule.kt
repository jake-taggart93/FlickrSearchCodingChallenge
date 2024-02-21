package com.example.cvscodechallenge.data.di

import com.example.cvscodechallenge.data.network.FlickrNetworkSource
import com.example.cvscodechallenge.data.network.FlickrService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {
    single<Moshi> { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }
    single<MoshiConverterFactory> { MoshiConverterFactory.create(get()) }
    single<Retrofit> { provideRetrofit(get()) }
    single<FlickrService> { provideFlickrService(retrofit = get()) }
    single<FlickrNetworkSource> { FlickrNetworkSource(flickrSearchService = get()) }
}

private const val BASE_URL = "https://api.flickr.com"

private fun provideRetrofit(moshiConverterFactory: MoshiConverterFactory): Retrofit {
    val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .build()
}

private fun provideFlickrService(retrofit: Retrofit): FlickrService =
    retrofit.create(FlickrService::class.java)

