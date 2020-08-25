package com.crud.poc.business

import com.crud.poc.dto.config.ConfigDto
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import javax.inject.Inject

class JwtService @Inject constructor(private val config: ConfigDto) {
    fun validate(credential: JWTCredential): Principal? =
            if (credential.payload.audience.contains(config.jwt.audience)) JWTPrincipal(credential.payload) else null
}