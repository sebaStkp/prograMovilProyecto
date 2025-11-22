package com.ucb.perritos.features.registroMascota.data.mapper

import com.ucb.perritos.features.registroMascota.data.database.entity.RegistroPerroEntity
import com.ucb.perritos.features.registroMascota.domain.model.PerroModel

fun RegistroPerroEntity.toModel(): PerroModel {
    return PerroModel(
        nombre_perro = nombrePerro ?: "",
        id_usuario = idUsuario ?: "",
        raza = raza ?: "",
        edad = edad ?: 0,
        descripcion = descripcion ?: "",
        foto_perro = fotoAvatar,

    )
}

fun PerroModel.toEntity(): RegistroPerroEntity {
    return RegistroPerroEntity(
        id = this.id ?: 0,
        nombrePerro = nombre_perro,
        idUsuario = id_usuario,
        raza = raza,
        edad = edad ?: 0,
        descripcion = descripcion,
        fotoAvatar = foto_perro ?: ""
    )
}
