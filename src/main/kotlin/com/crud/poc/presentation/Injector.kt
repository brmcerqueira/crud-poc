package com.crud.poc.presentation

import io.ktor.application.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

object Injector {
    val presentationComponent: PresentationComponent = DaggerPresentationComponent.builder().build()

    fun ApplicationCall.requestComponent(): RequestComponent = attributes.computeIfAbsent(AttributeKey("requestComponentKey")) {
        presentationComponent.requestComponent(RequestModule(this))
    }

    fun go(action: RequestComponent.() -> suspend () -> Unit): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit {
        return {
            call.requestComponent().action().invoke()
        }
    }
}