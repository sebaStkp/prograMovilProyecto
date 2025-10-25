package com.ucb.perritos.features.perfilPerro.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucb.perritos.features.perfilPerro.data.database.entity.FotoPerroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IFotoPerroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarFoto(foto: FotoPerroEntity)

//    @Query("DELETE FROM fotos_perro WHERE id = :fotoId")
//    suspend fun eliminarFotoPorId(fotoId: Long)

    @Query("SELECT * FROM fotos_perro WHERE perroId = :perroId ORDER BY timestamp DESC")
    fun observarFotosDePerro(perroId: Long): Flow<List<FotoPerroEntity>>
}