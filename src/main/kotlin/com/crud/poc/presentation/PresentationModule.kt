package com.crud.poc.presentation

import com.crud.poc.dto.config.ConfigDto
import com.crud.poc.dto.config.JwtDto
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
            connectionString = applicationConfig.property("connectionString").getString(),
            jwt = JwtDto(
                issuer = applicationConfig.property("jwt.issuer").getString(),
                audience = applicationConfig.property("jwt.audience").getString(),
                realm = applicationConfig.property("jwt.realm").getString(),
                subject = applicationConfig.property("jwt.subject").getString(),
                secret = applicationConfig.property("jwt.secret").getString()
            )
        )
    }
}