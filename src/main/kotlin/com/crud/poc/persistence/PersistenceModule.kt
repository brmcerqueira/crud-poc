package com.crud.poc.persistence

import dagger.Module
import dagger.Provides
import org.jetbrains.exposed.sql.Database

@Module
class PersistenceModule {
    @Provides
    fun provideDatabase(): Database = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
}