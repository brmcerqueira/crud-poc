package com.crud.poc.persistence.table

import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable() {
    val name = varchar("name", 50).index()
}