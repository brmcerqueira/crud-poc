package com.crud.poc.dto.config

data class ConfigDto(val port: Int, val connectionString: String, val jwt: JwtDto)