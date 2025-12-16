package com.ucb.perritos.registroUsuario.domain.model


import com.ucb.perritos.features.registroUsuario.domain.model.NombreDueño
import org.junit.Test
import org.junit.Assert.assertEquals

class NombreTest {

    @Test
    fun `nombre correcto se crea exitosamente`() {
        val nombre = NombreDueño("Juan Perez")
        assertEquals("Juan Perez", nombre.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el nombre esta vacio`() {
        NombreDueño("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el nombre tiene numeros`() {
        NombreDueño("Juan P3rez")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el nombre es demasiado largo`() {
        val nombreLargo = "A".repeat(51)
        NombreDueño(nombreLargo)
    }
}