package com.ucb.perritos.features.perfilPerro.domain.usecase

import android.net.Uri
import com.ucb.perritos.features.perfilPerro.data.datasource.FotoPerroRemoteSupabase

class SubirYGuardarFotoPerroUseCase(
    private val remote: FotoPerroRemoteSupabase,
    private val agregarFoto: AgregarFotoPerroUseCase
) {
    suspend operator fun invoke(
        perroId: Long,
        imageUri: Uri
    ) {
        val url = remote.uploadFotoPerro(
            bucket = "perro-fotos", // <-- tu bucket de fotos (NO el de avatar)
            perroId = perroId,
            imageUri = imageUri
        )
        agregarFoto(perroId, url)
    }
}
