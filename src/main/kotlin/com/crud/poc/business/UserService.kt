package com.crud.poc.business

import com.crud.poc.domain.User
import com.crud.poc.dto.UserSaveDto
import com.crud.poc.persistence.UserDao
import javax.inject.Inject

class UserService @Inject constructor(private val dao: UserDao) {
    fun create(dto: UserSaveDto) {
        dao.create(User(
            id = null,
            name = dto.name,
            email = dto.email,
        ))
    }

    fun update(id: Long, dto: UserSaveDto) {
        dao.update(User(
            id = id,
            name = dto.name,
            email = dto.email
        ))
    }
}