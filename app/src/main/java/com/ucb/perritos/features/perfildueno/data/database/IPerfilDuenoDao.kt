package com.ucb.perritos.features.perfildueno.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IPerfilDuenoDao {

    @Query("SELECT * FROM perfil_dueno LIMIT 1")
    suspend fun obtenerPerfil(): PerfilDuenoEntity?

    @Insert
    suspend fun insertarPerfil(perfil: PerfilDuenoEntity)

    @Query("DELETE FROM perfil_dueno")
    suspend fun eliminarPerfil()
}
