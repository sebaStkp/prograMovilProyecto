package com.ucb.perritos.registroMascota.domain.model

import com.ucb.perritos.features.registroMascota.domain.model.UsuarioId

import org.junit.Test
import org.junit.Assert.assertEquals

class UsuarioIdTest {

    @Test
    fun `id usuario se crea exitosamente`() {
        val id = UsuarioId("user_123")
        assertEquals("user_123", id.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el id esta vacio`() {
        UsuarioId("")
    }
}