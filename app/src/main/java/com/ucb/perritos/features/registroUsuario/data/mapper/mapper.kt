package com.ucb.perritos.features.registroUsuario.data.mapper

import com.ucb.perritos.features.registroMascota.data.database.entity.RegistroPerroEntity
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import com.ucb.perritos.features.registroUsuario.data.database.entity.RegistroUsuarioEntity
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel

fun RegistroUsuarioEntity.toModel() : UsuarioModel {
    return UsuarioModel(
        nombreDueño = nombreDueño,
        email = email,
        contraseña = contraseña,
    )
}

fun UsuarioModel.toEntity() : RegistroUsuarioEntity {
    return RegistroUsuarioEntity(
        nombreDueño = nombreDueño,
        email = email,
        contraseña = contraseña,
    )
}