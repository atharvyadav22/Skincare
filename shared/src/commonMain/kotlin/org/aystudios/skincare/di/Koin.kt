package org.aystudios.skincare.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration


fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        coroutineModule,
        platformSettingsModule,
        networkModule,
        dataModule,
        viewModelModule
    )
}