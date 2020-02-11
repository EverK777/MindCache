package com.example.mindvalleychallenge.di

import com.example.mindvalleychallenge.data.external.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single { Repository() }
}