package com.crud.poc.presentation

import com.crud.poc.presentation.Injector.go
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
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

    server.start(wait = true)
}
