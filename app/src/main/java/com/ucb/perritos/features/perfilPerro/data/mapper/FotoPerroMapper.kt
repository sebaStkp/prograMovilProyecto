package com.ucb.perritos.features.perfilPerro.data.mapper

import com.ucb.perritos.features.perfilPerro.data.database.entity.FotoPerroEntity
import com.ucb.perritos.features.perfilPerro.domain.model.FotoPerroModel

object FotoPerroMapper {

    fun toDomain(entity: FotoPerroEntity): FotoPerroModel {
        return FotoPerroModel(
            id = entity.id,
            perroId = entity.perroId,
            url = entity.url,
            timestamp = entity.timestamp
        )
    }

    fun toEntity(model: FotoPerroModel): FotoPerroEntity {
        return FotoPerroEntity(
            id = model.id,
            perroId = model.perroId,
            url = model.url,
            timestamp = model.timestamp
        )
    }
}