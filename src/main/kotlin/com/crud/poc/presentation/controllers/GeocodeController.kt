package com.crud.poc.presentation.controllers

import com.crud.poc.consumer.GoogleApiService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import javax.inject.Inject

class GeocodeController @Inject constructor(private val call: ApplicationCall, private val service: GoogleApiService) {
    suspend fun geocodeToAddress() {
        call.respond(HttpStatusCode.OK, service.geocodeToAddress(call.request.queryParameters["search"]!!))
    }
}