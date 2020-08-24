package com.crud.poc.presentation

import com.crud.poc.dto.ConfigDto
import com.typesafe.config.ConfigFactory
import dagger.Module
import dagger.Provides
import io.ktor.config.*
import io.ktor.util.*
import javax.inject.Singleton

@Module
class PresentationModule {
    @KtorExperimentalAPI
    @Singleton
    @Provides
    fun provideConfigDto(): ConfigDto {
        val applicationConfig = HoconApplicationConfig(ConfigFactory.load())
        return ConfigDto(
            port = applicationConfig.property("port").getString().toInt(),
            connectionString = applicationConfig.property("connectionString").getString()
        )
    }
}