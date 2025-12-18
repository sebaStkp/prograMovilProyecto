package com.ucb.perritos.appRoomDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

import com.ucb.perritos.features.perfilPerro.data.database.dao.IFotoPerroDao

import com.ucb.perritos.features.perfilPerro.data.database.dao.IPerfilPerroDao
import com.ucb.perritos.features.perfilPerro.data.database.entity.FotoPerroEntity
import com.ucb.perritos.features.perfilPerro.data.database.entity.PerfilPerroEntity
import com.ucb.perritos.features.registroMascota.data.database.dao.IRegistroPerroDao
import com.ucb.perritos.features.registroMascota.data.database.entity.RegistroPerroEntity
import com.ucb.perritos.features.registroUsuario.data.database.dao.IRegistroUsuarioDao
import com.ucb.perritos.features.registroUsuario.data.database.entity.RegistroUsuarioEntity

val MIGRATION_7_8 = object : Migration(7, 8){
    override fun migrate(database: SupportSQLiteDatabase){
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS fotos_perro (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                perroId INTEGER NOT NULL,
                url TEXT NOT NULL,
                timestamp INTEGER NOT NULL
            )
        """.trimIndent())
    }
}
@Database(entities = [RegistroPerroEntity::class, RegistroUsuarioEntity::class, PerfilPerroEntity::class,FotoPerroEntity::class], version = 8)
abstract class AppRoomDataBase : RoomDatabase() {
    abstract fun registroPerroDao(): IRegistroPerroDao
    abstract fun registroUsuarioDao(): IRegistroUsuarioDao

    abstract fun perfilPerroDao(): IPerfilPerroDao
    abstract fun fotoPerroDao(): IFotoPerroDao


    companion object {
        @Volatile
        private var Instance: AppRoomDataBase? = null


        fun getDatabase(context: Context): AppRoomDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppRoomDataBase::class.java, "proyecto_perritos_db")
                    .addMigrations(MIGRATION_7_8)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
