package com.crud.poc.persistence

import com.crud.poc.domain.User
import com.crud.poc.persistence.table.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import javax.inject.Inject

class UserDao @Inject constructor(private val database: Database) {
    fun create(entity: User): Long = transaction(database) {
        (Users.insert {
            it[name] = entity.name
        } get Users.id).value
    }

    fun update(entity: User) {
        transaction(database) {
            Users.update(where = {
                    Users.id.eq(entity.id)
                },
                body = {
                    it[name] = entity.name
                })
        }
    }
}