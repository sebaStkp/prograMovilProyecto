package com.ucb.perritos.registroUsuario.domain.model

import com.ucb.perritos.features.registroUsuario.domain.model.Password
import org.junit.Test
import org.junit.Assert.assertEquals

class PasswordTest {

    @Test
    fun `password segura se crea exitosamente`() {
        val pass = Password("Password123")
        assertEquals("Password123", pass.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si es muy corta`() {
        Password("Pass1")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si no tiene numeros`() {
        Password("Password")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si no tiene mayusculas`() {
        Password("password123")
    }
}