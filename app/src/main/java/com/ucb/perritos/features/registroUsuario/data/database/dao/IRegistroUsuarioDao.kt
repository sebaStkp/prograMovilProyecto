package com.ucb.perritos.features.registroUsuario.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucb.perritos.features.registroUsuario.data.database.entity.RegistroUsuarioEntity

@Dao
interface IRegistroUsuarioDao {
    @Query("SELECT * FROM usuarios")
    suspend fun getUsuarios(): List<RegistroUsuarioEntity>

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUsuarioPorEmail(email: String): RegistroUsuarioEntity?


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(usuario: RegistroUsuarioEntity)


    @Query("DELETE FROM usuarios")
    suspend fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsuarios(lists: List<RegistroUsuarioEntity>)
}