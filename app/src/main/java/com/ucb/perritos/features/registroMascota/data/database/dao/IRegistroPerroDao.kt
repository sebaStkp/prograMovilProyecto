package com.ucb.perritos.features.registroMascota.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucb.perritos.features.registroMascota.data.database.entity.RegistroPerroEntity

@Dao
interface IRegistroPerroDao {
    @Query("SELECT * FROM perros")
    suspend fun getPerros(): List<RegistroPerroEntity>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(perro: RegistroPerroEntity)


    @Query("DELETE FROM perros")
    suspend fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPerros(lists: List<RegistroPerroEntity>)
}