package com.crud.poc.presentation

import dagger.Component

@Component
interface PresentationComponent {
    fun requestComponent(module: RequestModule): RequestComponent
}