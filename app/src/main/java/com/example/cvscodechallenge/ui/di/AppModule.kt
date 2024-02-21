package com.example.cvscodechallenge.ui.di

import com.example.cvscodechallenge.ui.viewmodel.FlickrSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FlickrSearchViewModel(flickrSearchRepository = get()) }
}