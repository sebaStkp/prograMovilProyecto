//package com.ucb.perritos.features.buscarMascota.data.database.entity
//
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.ForeignKey
//import androidx.room.PrimaryKey
//import com.ucb.perritos.features.registroMascota.data.database.entity.RegistroPerroEntity
//
//
//@Entity(
//    tableName = "ubicacionPerro",
//    foreignKeys = [
//        ForeignKey(
//            entity = RegistroPerroEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["idPerro"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class BuscarMascotaEntity(
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
//    var id: Int = 0,
//    @ColumnInfo(name = "latitud")
//    var latitud: Double = 0.0,
//    @ColumnInfo(name = "longitud")
//    var longitud: Double = 0.0,
//    @ColumnInfo(name = "direccion")
//    var direccion: String? = null,
//    @ColumnInfo(name = "idPerro")
//    var idPerro: Int? = null,
//)
