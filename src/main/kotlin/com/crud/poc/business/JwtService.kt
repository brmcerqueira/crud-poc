package com.crud.poc.business

import com.crud.poc.domain.Permission
import com.crud.poc.domain.User
import com.crud.poc.dto.SignInDto
import com.crud.poc.persistence.UserDao
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import javax.inject.Inject

class JwtService @Inject constructor(private val dao: UserDao) {
    fun validate(credential: JWTCredential, permissions: List<Permission>): Principal? =
            if (credential.payload.claims["permissions"]
            ?.asArray(String::class.java)
            ?.map { Permission.valueOf(it) }
            ?.any { permissions.contains(it) } == true) JWTPrincipal(credential.payload) else null

    fun signIn(dto: SignInDto): Pair<User, List<Permission>> {
       return Pair(dao.getByEmail(dto.email), listOf(Permission.UpdateUser))
    }
}