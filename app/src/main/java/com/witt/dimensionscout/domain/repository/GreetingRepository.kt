package com.witt.dimensionscout.domain.repository

import com.witt.dimensionscout.domain.model.Greeting

interface GreetingRepository {
    suspend fun getGreeting(): Greeting
}
