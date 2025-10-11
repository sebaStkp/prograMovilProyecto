package com.ucb.perritos.appRoomDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ucb.perritos.features.registroMascota.data.database.dao.IRegistroPerroDao
import com.ucb.perritos.features.registroMascota.data.database.entity.RegistroPerroEntity


@Database(entities = [RegistroPerroEntity::class], version = 1)
abstract class AppRoomDataBase : RoomDatabase() {
    abstract fun registroPerroDao(): IRegistroPerroDao


    companion object {
        @Volatile
        private var Instance: AppRoomDataBase? = null


        fun getDatabase(context: Context): AppRoomDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppRoomDataBase::class.java, "proyecto_perros_db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
