package com.crud.poc.persistence

import com.crud.poc.dto.config.ConfigDto
import dagger.Module
import dagger.Provides
import org.jetbrains.exposed.sql.Database

@Module
class PersistenceModule {
    @Provides
    fun provideDatabase(config: ConfigDto): Database = Database.connect(config.connectionString, driver = "org.postgresql.Driver")
}