package com.ucb.perritos.features.perfilPerro.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fotos_perro")
data class FotoPerroEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val perroId: Long,
    val url: String,
    val timestamp: Long
)