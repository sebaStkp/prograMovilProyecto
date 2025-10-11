package com.ucb.perritos.features.registroMascota.data.mapper

import com.ucb.perritos.features.registroMascota.data.database.entity.RegistroPerroEntity
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel

fun RegistroPerroEntity.toModel() : RegistroPerroModel {
    return RegistroPerroModel(
        nombrePerro = nombrePerro,
        raza = raza,
        edad = edad,
        descripcion = descripcion
    )
}

fun RegistroPerroModel.toEntity() : RegistroPerroEntity {
    return RegistroPerroEntity(
        nombrePerro = nombrePerro,
        raza = raza,
        edad = edad ?: 0,
        descripcion = descripcion
    )
}