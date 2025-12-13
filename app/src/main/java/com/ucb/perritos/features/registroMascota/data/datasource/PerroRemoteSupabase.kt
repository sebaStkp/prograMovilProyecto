package com.ucb.perritos.features.registroMascota.data.datasource


import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
import com.ucb.perritos.features.registroMascota.domain.model.PerroModel

import io.github.jan.supabase.SupabaseClient

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class PerroRemoteSupabase(
    private val supabase: SupabaseClient
) {
    suspend fun registrarEnSupabase(perro: PerroModel,avatarBytes: ByteArray?): PerroModel {


        val userId = supabase.auth.currentUserOrNull()?.id
            ?: throw Exception("No hay usuario logueado para registrar la mascota")

        var avatarUrl: String? = null

        if (avatarBytes != null) {
            val fileName = "avatar_${userId}_${System.currentTimeMillis()}.jpg"
            val path = "avatars/$fileName"   // carpeta dentro del bucket

            // 👇 bucket que tú crees en Supabase Storage
            supabase.storage
                .from("perros_avatars")
                .upload(path, avatarBytes)

            // Ojo: según versión puede ser publicUrl(...) o getPublicUrl(...)
            avatarUrl = supabase.storage.from("perros_avatars").publicUrl(path)           // si te marca error, prueba getPublicUrl(path)
        }

        val perroDto = PerroDto(
            nombre_perro = perro.nombre_perro ?: "",
            raza = perro.raza ?: "",
            edad = perro.edad ?: 0,
            descripcion = perro.descripcion ?: "",
            id_usuario = userId,
            foto_perro = avatarUrl
        )


        supabase.from("perritos").insert(perroDto)


        try {
            supabase.auth.updateUser {
                data = buildJsonObject {
                    put("tiene_perro", true)
                }
            }
        } catch (e: Exception) {
            println("No se pudo actualizar la etiqueta tiene_perro: ${e.message}")
        }

        return perro.copy(id_usuario = userId,foto_perro = avatarUrl)
    }

    suspend fun getAllPerros(id_usuario: String): List<PerroDto> {
        return try {
            println("Buscando perros para id_usuario: $id_usuario")

            val listaPerros = supabase.from("perritos")
                .select {
                    filter {
                        eq("id_usuario", id_usuario)
                    }
                }
                .decodeList<PerroDto>()

            println("Perros encontrados: $listaPerros")
            listaPerros
        } catch (e: Exception) {
            println("Error obteniendo perros: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }
    suspend fun editarPerro(perro: PerroModel, id_perro: Int): Result<PerroModel> {
        return try {
            println("Intentando editar perro con ID: ${id_perro}")

            val idPerro = id_perro ?: throw Exception("El modelo de perro no tiene ID, no se puede editar.")

            val perroDto = PerroDto(
                nombre_perro = perro.nombre_perro ?: "",
                raza = perro.raza ?: "",
                edad = perro.edad ?: 0,
                descripcion = perro.descripcion ?: "",
                id_usuario = perro.id_usuario ?: ""
            )

            supabase.from("perritos").update(perroDto) {
                filter {
                    eq("id", idPerro)
                }
            }

            println("Perro editado correctamente en Supabase")
            Result.success(perro)

        } catch (e: Exception) {
            println("Error al editar perro en Supabase: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

}