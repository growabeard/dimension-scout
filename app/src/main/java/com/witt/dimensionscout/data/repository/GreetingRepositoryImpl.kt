package com.witt.dimensionscout.data.repository

import com.witt.dimensionscout.domain.model.Greeting
import com.witt.dimensionscout.domain.repository.GreetingRepository
import kotlinx.coroutines.delay

class GreetingRepositoryImpl : GreetingRepository {
    override suspend fun getGreeting(): Greeting {
        delay(500)
        return Greeting(message = "Hello from Clean Architecture!")
    }
}
