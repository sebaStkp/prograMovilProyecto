package com.ucb.perritos.appRoomDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ucb.perritos.features.perfilPerro.data.database.dao.IPerfilPerroDao
import com.ucb.perritos.features.perfilPerro.data.database.entity.PerfilPerroEntity
import com.ucb.perritos.features.registroMascota.data.database.dao.IRegistroPerroDao
import com.ucb.perritos.features.registroMascota.data.database.entity.RegistroPerroEntity
import com.ucb.perritos.features.registroUsuario.data.database.dao.IRegistroUsuarioDao
import com.ucb.perritos.features.registroUsuario.data.database.entity.RegistroUsuarioEntity


@Database(entities = [RegistroPerroEntity::class, RegistroUsuarioEntity::class, PerfilPerroEntity::class], version = 3)
abstract class AppRoomDataBase : RoomDatabase() {
    abstract fun registroPerroDao(): IRegistroPerroDao
    abstract fun registroUsuarioDao(): IRegistroUsuarioDao

    abstract fun perfilPerroDao(): IPerfilPerroDao


    companion object {
        @Volatile
        private var Instance: AppRoomDataBase? = null


        fun getDatabase(context: Context): AppRoomDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppRoomDataBase::class.java, "proyecto_perritos_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
