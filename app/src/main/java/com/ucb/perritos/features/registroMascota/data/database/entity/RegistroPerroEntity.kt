package com.ucb.perritos.features.registroMascota.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perros")
data class RegistroPerroEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nombrePerro")
    var nombrePerro: String? = null,

    @ColumnInfo(name = "raza")
    var raza: String? = null,

    @ColumnInfo(name = "edad")
    var edad: Int = 0,

    @ColumnInfo(name = "descripcion")
    var descripcion: String? = null,

    @ColumnInfo(name = "idUsuario")
    var idUsuario: String? = null,
)
