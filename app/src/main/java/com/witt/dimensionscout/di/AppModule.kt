package com.witt.dimensionscout.di

import com.witt.dimensionscout.data.remote.CharacterApiService
import com.witt.dimensionscout.data.repository.CharacterRepositoryImpl
import com.witt.dimensionscout.data.repository.GreetingRepositoryImpl
import com.witt.dimensionscout.domain.repository.CharacterRepository
import com.witt.dimensionscout.domain.repository.GreetingRepository
import com.witt.dimensionscout.domain.use_case.GetCharacterUseCase
import com.witt.dimensionscout.domain.use_case.GetGreetingUseCase
import com.witt.dimensionscout.presentation.characters.CharacterSearchViewModel
import com.witt.dimensionscout.presentation.greeting.GreetingViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(CharacterApiService::class.java) }

    singleOf(::GreetingRepositoryImpl) bind GreetingRepository::class
    singleOf(::CharacterRepositoryImpl) bind CharacterRepository::class

    factoryOf(::GetGreetingUseCase)
    factoryOf(::GetCharacterUseCase)

    viewModelOf(::GreetingViewModel)
    viewModelOf(::CharacterSearchViewModel)
}
