package com.ucb.perritos.features.registroMascota.data.mapper

import com.ucb.perritos.features.registroMascota.data.database.entity.RegistroPerroEntity
import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
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

fun RegistroPerroEntity.toDto(): PerroDto {
    return PerroDto(
        id = this.id,
        nombre_perro = this.nombrePerro ?: "",
        raza = this.raza ?: "",
        edad = this.edad,
        descripcion = this.descripcion ?: "",
        id_usuario = this.idUsuario ?: ""
    )
}

fun PerroDto.toEntity(): RegistroPerroEntity {
    return RegistroPerroEntity(
        id = this.id ?: 0,
        nombrePerro = this.nombre_perro,
        raza = this.raza,
        edad = this.edad,
        descripcion = this.descripcion,
        idUsuario = this.id_usuario,
        fotoAvatar = ""
    )
}
