package com.ucb.perritos.features.registroMascota.data.repository

import com.ucb.perritos.features.registroMascota.data.datasource.PerroLocalDataSource
import com.ucb.perritos.features.registroMascota.data.datasource.PerroRemoteSupabase
import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
import com.ucb.perritos.features.registroMascota.data.mapper.toDto
import com.ucb.perritos.features.registroMascota.data.mapper.toEntity
import com.ucb.perritos.features.registroMascota.domain.model.PerroModel
import com.ucb.perritos.features.registroMascota.domain.repository.IRegistroPerroRepository
import kotlinx.coroutines.flow.onEach


class RegistroPerroRepository(
    private val registroPerroLocalDataSource: PerroLocalDataSource,
    private val registroPerroSupabase: PerroRemoteSupabase
): IRegistroPerroRepository {
    override suspend fun registrarPerro(perro: PerroModel,avatarBytes: ByteArray?): Result<PerroModel> {
        return try {
            //registroPerroSupabase.registrarEnSupabase(perro)
            //registroPerroLocalDataSource.insert(perro)
            //Result.success(perro)
            val perroConDatos = registroPerroSupabase.registrarEnSupabase(perro, avatarBytes)
            // 2) Guardar en Room local
            registroPerroLocalDataSource.insert(perroConDatos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun obtenerPerros(id_usuario: String): Result<List<PerroDto>> {
        return try {
            // 1. Intentamos obtener de Supabase (Internet)
            val perritosRemotos = registroPerroSupabase.getAllPerros(id_usuario)

            // 2. Si tuvimos éxito, actualizamos la base de datos local (Cache)
            // Convertimos los DTOs remotos a Entidades locales y guardamos
            registroPerroLocalDataSource.actualizarCache(perritosRemotos.map { it.toEntity() })

            // Devolvemos los datos frescos
            Result.success(perritosRemotos)

        } catch (e: Exception) {
            // 3. Si falló (ej. No hay internet), vamos a la base de datos LOCAL
            println("Fallo remoto, intentando local: ${e.message}")

            try {
                val perritosLocales = registroPerroLocalDataSource.obtenerTodos()

                if (perritosLocales.isNotEmpty()) {
                    // Convertimos Entidades locales a DTOs para que la app entienda
                    val listaDto = perritosLocales.map { it.toDto() }
                    Result.success(listaDto)
                } else {
                    // Si local también está vacío, entonces sí devolvemos el error original
                    Result.failure(e)
                }
            } catch (localEx: Exception) {
                Result.failure(localEx)
            }
        }
    }


    override suspend fun editarPerro(perro: PerroModel, id_perro: Int): Result<PerroModel> {
        val resultRemote = registroPerroSupabase.editarPerro(perro, id_perro)

        resultRemote.onSuccess {
            try {
                registroPerroLocalDataSource.editarPerro(it, id_perro)
            } catch (e: Exception) {
                println("Advertencia: Se actualizó en nube pero falló en local: ${e.message}")
            }
        }

        return resultRemote
    }
}