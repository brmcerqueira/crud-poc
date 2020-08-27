package com.crud.poc.presentation

import com.crud.poc.business.JwtService
import com.crud.poc.persistence.PersistenceModule
import com.crud.poc.presentation.controllers.GeocodeController
import com.crud.poc.presentation.controllers.JwtController
import com.crud.poc.presentation.controllers.UserController
import dagger.Subcomponent

@RequestScope
@Subcomponent(modules = [(RequestModule::class),
    (PersistenceModule::class)])
interface RequestComponent {
    val jwtService: JwtService
    val userController: UserController
    val jwtController: JwtController
    val geocodeController: GeocodeController
}