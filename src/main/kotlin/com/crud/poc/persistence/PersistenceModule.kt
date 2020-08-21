package com.crud.poc.persistence

import dagger.Module
import dagger.Provides
import org.jetbrains.exposed.sql.Database

@Module
class PersistenceModule {
    @Provides
    fun provideDatabase(): Database = Database.connect("jdbc:postgresql://localhost:5432/crud-poc?user=admin&password=admin", driver = "org.postgresql.Driver")
}