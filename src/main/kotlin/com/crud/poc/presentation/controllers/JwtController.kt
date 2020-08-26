package com.crud.poc.presentation.controllers

import com.crud.poc.business.JwtService
import com.crud.poc.presentation.Injector.buildToken
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import javax.inject.Inject

class JwtController @Inject constructor(private val call: ApplicationCall, private val service: JwtService) {
    suspend fun signIn() {
        val pair = service.signIn(call.receive())
        call.respond(HttpStatusCode.OK, buildToken(pair.first, pair.second))
    }
}