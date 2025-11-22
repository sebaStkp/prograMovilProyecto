package com.ucb.perritos.features.registroMascota.data.datasource


import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
import com.ucb.perritos.features.registroMascota.domain.model.PerroModel

import io.github.jan.supabase.SupabaseClient

import io.github.jan.supabase.auth.auth

import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class PerroRemoteSupabase(
    private val supabase: SupabaseClient
) {
    suspend fun registrarEnSupabase(perro: PerroModel): PerroModel {


        val userId = supabase.auth.currentUserOrNull()?.id
            ?: throw Exception("No hay usuario logueado para registrar la mascota")


        val perroDto = PerroDto(
            nombre_perro = perro.nombre_perro ?: "",
            raza = perro.raza ?: "",
            edad = perro.edad ?: 0,
            descripcion = perro.descripcion ?: "",
            id_usuario = userId
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

        return perro.copy(id_usuario = userId)
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