package com.crud.poc.presentation

import com.crud.poc.dto.ConfigDto
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(PresentationModule::class)])
interface PresentationComponent {
    val config: ConfigDto
    fun requestComponent(module: RequestModule): RequestComponent
}