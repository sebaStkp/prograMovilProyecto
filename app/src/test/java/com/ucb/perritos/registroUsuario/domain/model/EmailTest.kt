package com.ucb.perritos.registroUsuario.domain.model


import com.ucb.perritos.features.registroUsuario.domain.model.Email
import org.junit.Test
import org.junit.Assert.assertEquals

class EmailTest {

    @Test
    fun `email valido se crea exitosamente`() {
        val email = Email("usuario@google.com")
        assertEquals("usuario@google.com", email.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si falta el arroba`() {
        Email("usuariogoogle.com")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si empieza con espacios`() {
        Email(" usuario@google.com")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si esta vacio`() {
        Email("")
    }
}