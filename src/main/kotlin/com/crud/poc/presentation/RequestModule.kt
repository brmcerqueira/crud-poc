package com.crud.poc.presentation

import dagger.Module
import dagger.Provides
import io.ktor.application.*

@Module
class RequestModule constructor(private val applicationCall: ApplicationCall) {
    @Provides
    fun provideApplicationCall() = applicationCall
}