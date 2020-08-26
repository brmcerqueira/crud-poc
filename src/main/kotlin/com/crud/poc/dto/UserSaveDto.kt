package com.crud.poc.dto

import kotlinx.serialization.*

@Serializable
data class UserSaveDto(val name: String, val email: String)