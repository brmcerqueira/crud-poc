package com.crud.poc.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val latitude: Double,
    val longitude: Double,
    val street: String,
    val number: String,
    val neighborhood: String,
    val city: String,
    val state: String,
    val postalCode: String
)