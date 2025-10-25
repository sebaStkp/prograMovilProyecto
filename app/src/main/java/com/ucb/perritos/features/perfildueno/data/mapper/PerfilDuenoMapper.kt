package com.ucb.perritos.features.perfildueno.data.mapper

import com.ucb.perritos.features.perfildueno.data.database.PerfilDuenoEntity
import com.ucb.perritos.features.perfildueno.domain.model.PerfilDuenoModel

class PerfilDuenoMapper {
    fun entityToModel(entity: PerfilDuenoEntity?): PerfilDuenoModel? {
        return entity?.let {
            PerfilDuenoModel(
                id = it.id,
                nombre = it.nombre,
                email = it.email,
                telefono = it.telefono
            )
        }
    }

    fun modelToEntity(model: PerfilDuenoModel): PerfilDuenoEntity {
        return PerfilDuenoEntity(
            id = model.id,
            nombre = model.nombre,
            email = model.email,
            telefono = model.telefono
        )
    }
}

