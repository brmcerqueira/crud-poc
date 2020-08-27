package com.crud.poc.consumer

import com.crud.poc.dto.AddressDto
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Named

class GoogleApiService @Inject constructor(@Named("googleApi") private val client: HttpClient, private val config: GoogleApiConfig) {
    @Serializable
    private data class Geocoding(
        val results: Array<Result>,
        val status: String
    )

    @Serializable
    private data class Result(
        val address_components: Array<AddressComponent>,
        val formatted_address: String,
        val geometry: Geometry,
        val place_id: String,
        val types: Array<String>
    )

    @Serializable
    private data class AddressComponent(
        val long_name: String,
        val short_name: String,
        val types: Array<String>
    )

    @Serializable
    private data class Geometry(
        val location: Location,
        val location_type: String,
        val viewport: Viewport
    )

    @Serializable
    private data class Location(
        val lat: Double,
        val lng: Double
    )

    @Serializable
    private data class Viewport(
        val northeast: Location,
        val southwest: Location
    )

    private suspend fun geocode(search: String) = client.get<Geocoding>("/maps/api/geocode/json?address=$search&sensor=false&key=${config.key}")

    suspend fun geocodeToAddress(search: String): List<AddressDto> {
        val geocoding = geocode(search)

        val results = geocoding.results.filter { it.geometry.location.lat != 0.0 && it.geometry.location.lng != 0.0 }

        if (!results.any())
            return listOf()

        return results.map { result ->
            var number = ""
            var street = ""
            var neighborhood = ""
            var city = ""
            var state = ""
            var postalCode = ""

            result.address_components.forEach {
                when (it.types.first()) {
                    "street_number" -> number = it.long_name
                    "route" -> street = it.long_name
                    "political" -> neighborhood = it.long_name
                    "administrative_area_level_2" -> city = it.long_name.replace('-', '_')
                    "administrative_area_level_1" -> state = it.short_name
                    "postal_code" -> postalCode = it.long_name.replace("-", "")
                }
            }

            AddressDto(
                latitude = result.geometry.location.lat,
                longitude = result.geometry.location.lng,
                street = street,
                number = number,
                neighborhood = neighborhood,
                city = city,
                state = state,
                postalCode = postalCode
            )
        }
    }
}