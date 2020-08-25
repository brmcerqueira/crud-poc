package com.crud.poc.presentation

import com.crud.poc.domain.Permission
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

object Injector {
    val presentationComponent: PresentationComponent = DaggerPresentationComponent.builder().build()

    private fun ApplicationCall.requestComponent(permissions: List<Permission> = listOf()): RequestComponent = attributes.computeIfAbsent(AttributeKey("requestComponentKey")) {
        presentationComponent.requestComponent(RequestModule(this, permissions))
    }

    fun JWTAuthenticationProvider.Configuration.validateJwt() {
        validate {
            requestComponent().jwtService.validate(it)
        }
    }

    fun Route.allow(vararg permissions: Permission, build: Route.() -> Unit): Route {
        val configurationNames = listOf<String?>(null)
        val authenticatedRoute = createChild(AuthenticationRouteSelector(configurationNames))
        authenticatedRoute.intercept(ApplicationCallPipeline.Setup) {
            call.requestComponent(permissions.asList())
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