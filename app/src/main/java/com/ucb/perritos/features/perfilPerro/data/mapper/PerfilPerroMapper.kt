package com.ucb.perritos.features.perfilPerro.data.mapper

import com.ucb.perritos.features.perfilPerro.data.database.entity.PerfilPerroEntity
import com.ucb.perritos.features.perfilPerro.domain.model.PerfilPerroModel

object PerfilPerroMapper {
    fun toDomain(e: PerfilPerroEntity?) = e?.let {
        PerfilPerroModel(it.perroId, it.nombre, it.raza,edad = it.edad,
            descripcion = it.descripcion,it.avatarUrl)
    }
}