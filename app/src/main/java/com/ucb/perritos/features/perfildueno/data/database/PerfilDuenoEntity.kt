package com.ucb.perritos.features.perfildueno.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perfil_dueno")
data class PerfilDuenoEntity(
    @PrimaryKey
    val id: String = "owner",
    val nombre: String = "",
    val email: String = "",
    val telefono: String = ""
)
