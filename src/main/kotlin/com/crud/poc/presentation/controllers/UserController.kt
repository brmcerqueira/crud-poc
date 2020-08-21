package com.crud.poc.presentation.controllers

import com.crud.poc.business.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import javax.inject.Inject

class UserController @Inject constructor(private val call: ApplicationCall, private val service: UserService) {
    suspend fun create() {
        service.create(call.receive())
        call.respond(HttpStatusCode.OK)
    }

    suspend fun update() {
        service.update(call.parameters["id"]!!.toLong(), call.receive())
        call.respond(HttpStatusCode.OK)
    }
}