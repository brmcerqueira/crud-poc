package com.crud.poc.presentation

import com.crud.poc.consumer.ConsumeModule
import com.crud.poc.dto.config.ConfigDto
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(PresentationModule::class),
    (ConsumeModule::class)])
interface PresentationComponent {
    val config: ConfigDto
    fun requestComponent(module: RequestModule): RequestComponent
}