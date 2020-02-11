package com.example.mindvalleychallenge.di

import com.example.mindvalleychallenge.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
}