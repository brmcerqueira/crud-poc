package com.crud.poc.dto.config

data class JwtDto(val issuer: String, val audience: String, val realm: String, val secret: String)