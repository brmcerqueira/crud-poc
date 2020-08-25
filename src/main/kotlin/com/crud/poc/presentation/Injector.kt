package com.crud.poc.presentation

import io.ktor.application.*
import io.ktor.util.pipeline.*

object Injector {
    val presentationComponent: PresentationComponent = DaggerPresentationComponent.builder().build()

    fun go(action: RequestComponent.() -> suspend () -> Unit): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit {
        return {
            val requestComponent = presentationComponent.requestComponent(RequestModule(call))
            requestComponent.action().invoke()
        }
    }
}