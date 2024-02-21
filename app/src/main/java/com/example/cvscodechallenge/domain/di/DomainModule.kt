package com.example.cvscodechallenge.domain.di

import com.example.cvscodechallenge.data.repository.FlickrSearchRepositoryImpl
import com.example.cvscodechallenge.domain.repository.FlickrSearchRepository
import org.koin.dsl.module

val domainModule = module {
    single<FlickrSearchRepository> { FlickrSearchRepositoryImpl(networkSource = get()) }
}