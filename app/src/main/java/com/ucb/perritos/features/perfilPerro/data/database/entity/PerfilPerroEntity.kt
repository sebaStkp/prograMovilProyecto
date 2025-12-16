package com.ucb.perritos.features.perfilPerro.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "perfiles_perro")
data class PerfilPerroEntity(
    @PrimaryKey val perroId: Long,
    val nombre: String,
    val raza: String,
    val edad: Int?,
    val descripcion: String?,
    val avatarUrl: String?
)