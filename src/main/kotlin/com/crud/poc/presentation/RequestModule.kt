package com.crud.poc.presentation

import com.crud.poc.domain.Permission
import dagger.Module
import dagger.Provides
import io.ktor.application.*

@Module
class RequestModule constructor(private val applicationCall: ApplicationCall, private val permissions: List<Permission>) {
    @Provides
    fun provideApplicationCall() = applicationCall

    @Provides
    fun providePermissions() = permissions
}