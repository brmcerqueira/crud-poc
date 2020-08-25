package com.crud.poc.presentation

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.crud.poc.presentation.Injector.allow
import com.crud.poc.presentation.Injector.go
import com.crud.poc.presentation.Injector.presentationComponent
import com.crud.poc.presentation.Injector.validateJwt
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
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

            install(Authentication) {
                jwt {
                    realm = presentationComponent.config.jwt.realm
                    verifier(JWT.require(Algorithm.HMAC256(presentationComponent.config.jwt.secret))
                            .withAudience(presentationComponent.config.jwt.audience)
                            .withIssuer(presentationComponent.config.jwt.issuer)
                            .build())
                    validateJwt()
                }
            }

            routing {
                post("/user", go { userController::create })
                allow {
                    put("/user/{id}", go { userController::update })
                }
            }
        }

        connector {
            port = presentationComponent.config.port
            host = "0.0.0.0"
        }
    }).start(wait = true)
}