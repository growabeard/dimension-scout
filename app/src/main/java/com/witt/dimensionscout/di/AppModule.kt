package com.witt.dimensionscout.di

import com.witt.dimensionscout.data.repository.GreetingRepositoryImpl
import com.witt.dimensionscout.domain.repository.GreetingRepository
import com.witt.dimensionscout.domain.use_case.GetGreetingUseCase
import com.witt.dimensionscout.presentation.greeting.GreetingViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::GreetingRepositoryImpl) bind GreetingRepository::class

    factoryOf(::GetGreetingUseCase)

    viewModelOf(::GreetingViewModel)
}
