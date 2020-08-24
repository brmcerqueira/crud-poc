package com.crud.poc.presentation

import com.crud.poc.presentation.Injector.go
import com.crud.poc.presentation.Injector.presentationComponent
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.serialization.json.Json

@KtorExperimentalAPI
fun main() {
    embeddedServer(Netty, applicationEngineEnvironment {
        module {
            install(ContentNegotiation) {
                json(contentType = ContentType.Application.Json,
                        json = Json {
                            encodeDefaults = true
                            isLenient = true
                            allowSpecialFloatingPointValues = true
                            allowStructuredMapKeys = true
                            prettyPrint = true
                            useArrayPolymorphism = true
                        }
                )
            }
            routing {
                post("/user", go { userController::create })
                put("/user/{id}", go { userController::update })
            }
        }

        connector {
            port = presentationComponent.config.port
            host = "0.0.0.0"
        }
    }).start(wait = true)
}
