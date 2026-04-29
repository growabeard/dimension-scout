package com.witt.dimensionscout.domain.use_case

import com.witt.dimensionscout.domain.model.Greeting
import com.witt.dimensionscout.domain.repository.GreetingRepository

class GetGreetingUseCase(
    private val repository: GreetingRepository
) {
    suspend operator fun invoke(): Greeting {
        return repository.getGreeting()
    }
}
