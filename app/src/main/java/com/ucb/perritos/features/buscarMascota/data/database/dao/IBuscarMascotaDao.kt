package com.ucb.perritos.features.buscarMascota.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucb.perritos.features.buscarMascota.data.database.entity.BuscarMascotaEntity


@Dao
interface IBuscarMascotaDao {
    @Query("SELECT * FROM ubicacionPerro")
    suspend fun getPerros(): List<BuscarMascotaEntity>
}
