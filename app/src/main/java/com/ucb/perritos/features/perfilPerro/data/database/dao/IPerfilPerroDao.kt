package com.ucb.perritos.features.perfilPerro.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucb.perritos.features.perfilPerro.data.database.entity.PerfilPerroEntity
import com.ucb.perritos.features.perfilPerro.domain.model.PerfilPerroModel
import kotlinx.coroutines.flow.Flow


@Dao
interface IPerfilPerroDao {
    @Query("SELECT * FROM perfiles_perro WHERE perroId = :perroId LIMIT 1")
    fun observePerfil(perroId: Long): Flow<PerfilPerroEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPerfil(perfil: PerfilPerroEntity)
}