package com.crud.poc.consumer

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.config.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import javax.inject.Named
import javax.inject.Singleton

@Module
class ConsumeModule {
    @KtorExperimentalAPI
    @Provides
    @Singleton
    fun provideGoogleApiConfig(@Named("consume") config: ApplicationConfig): GoogleApiConfig {
        val consumeConfig = config.config("googleApi")
        return GoogleApiConfig(
            host = consumeConfig.property("host").getString(),
            key = consumeConfig.property("key").getString()
        )
    }

    @Provides
    @Singleton
    @Named("googleApi")
    fun provideHttpClientGoogleApi(config: GoogleApiConfig) = HttpClient(OkHttp) {
        defaultRequest {
            host = config.host
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                allowStructuredMapKeys = true
                prettyPrint = true
                useArrayPolymorphism = true
            })
        }
    }
}