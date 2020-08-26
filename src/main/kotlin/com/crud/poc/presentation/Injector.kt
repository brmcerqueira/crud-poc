package com.crud.poc.presentation

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.crud.poc.domain.Permission
import com.crud.poc.domain.User
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import java.util.Date

object Injector {
    val presentationComponent: PresentationComponent = DaggerPresentationComponent.builder().build()
    private val algorithm = Algorithm.HMAC256(presentationComponent.config.jwt.secret)
    private val permissionsKey = AttributeKey<List<Permission>>("permissionsKey")

    private fun ApplicationCall.requestComponent(): RequestComponent = attributes.computeIfAbsent(AttributeKey("requestComponentKey")) {
        presentationComponent.requestComponent(RequestModule(this))
    }

    fun Authentication.Configuration.setupJwt() {
        jwt {
            realm = presentationComponent.config.jwt.realm
            verifier(JWT.require(algorithm)
                    .withAudience(presentationComponent.config.jwt.audience)
                    .withIssuer(presentationComponent.config.jwt.issuer)
                    .build())
            validate {
                requestComponent().jwtService.validate(it, attributes[permissionsKey])
            }
        }
    }

    fun buildToken(user: User, permissions: List<Permission>): String = JWT.create()
            .withSubject(presentationComponent.config.jwt.subject)
            .withAudience(presentationComponent.config.jwt.audience)
            .withIssuer(presentationComponent.config.jwt.issuer)
            .withClaim("id", user.id)
            .withArrayClaim("permissions", permissions.map { it.name }.toTypedArray())
            //10 horas
            .withExpiresAt(Date(System.currentTimeMillis() + 36_000_00 * 10))
            .sign(algorithm)

    fun Route.allow(vararg permissions: Permission, build: Route.() -> Unit): Route {
        val configurationNames = listOf<String?>(null)
        val authenticatedRoute = createChild(AuthenticationRouteSelector(configurationNames))
        authenticatedRoute.intercept(ApplicationCallPipeline.Setup) {
            call.attributes.put(permissionsKey, permissions.asList())
        }
        application.feature(Authentication).interceptPipeline(authenticatedRoute, configurationNames, optional = false)
        authenticatedRoute.build()
        return authenticatedRoute
    }

    fun go(action: RequestComponent.() -> suspend () -> Unit): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit {
        return {
            call.requestComponent().action().invoke()
        }
    }
}