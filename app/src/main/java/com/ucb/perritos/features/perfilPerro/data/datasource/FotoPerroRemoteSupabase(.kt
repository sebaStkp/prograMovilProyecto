package com.ucb.perritos.features.perfilPerro.data.datasource

import android.content.ContentResolver
import android.net.Uri
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import java.util.UUID

class FotoPerroRemoteSupabase(
    private val supabase: SupabaseClient,
    private val contentResolver: ContentResolver
) {
    suspend fun uploadFotoPerro(
        bucket: String,           // 👈 perro-fotos
        perroId: Long,
        imageUri: Uri
    ): String {

        // 1️⃣ Validar que sea imagen
        val mime = contentResolver.getType(imageUri) ?: ""
        require(mime.startsWith("image/")) { "Solo se permiten imágenes" }

        // 2️⃣ Leer bytes
        val bytes = contentResolver.openInputStream(imageUri)
            ?.use { it.readBytes() }
            ?: error("No se pudo leer la imagen")

        // 3️⃣ Extensión
        val ext = when (mime) {
            "image/png" -> "png"
            "image/webp" -> "webp"
            else -> "jpg"
        }

        // 4️⃣ Path en Storage
        val path = "perros/$perroId/${System.currentTimeMillis()}_${UUID.randomUUID()}.$ext"

        // 5️⃣ Upload CORRECTO
        supabase.storage.from(bucket).upload(
            path = path,
            data = bytes
        ) {
            upsert = true
        }

        // 6️⃣ URL pública
        return supabase.storage.from(bucket).publicUrl(path)
    }
}
