package com.crud.poc.business

import com.crud.poc.domain.User
import com.crud.poc.dto.UserCreateDto
import com.crud.poc.persistence.UserDao
import javax.inject.Inject

class UserService @Inject constructor(private val dao: UserDao) {
    fun create(dto: UserCreateDto) {
        dao.create(
            User(
            id = null,
            name = dto.name
        )
        )
    }
}