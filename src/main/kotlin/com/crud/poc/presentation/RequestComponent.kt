package com.crud.poc.presentation

import com.crud.poc.persistence.PersistenceModule
import com.crud.poc.presentation.controllers.UserController
import dagger.Subcomponent

@RequestScope
@Subcomponent(modules = [(RequestModule::class),
    (PersistenceModule::class)])
interface RequestComponent {
    val userController: UserController
}