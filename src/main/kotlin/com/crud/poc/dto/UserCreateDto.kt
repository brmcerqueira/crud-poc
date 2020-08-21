package com.crud.poc.dto

import kotlinx.serialization.*

@Serializable
data class UserCreateDto(val name: String)