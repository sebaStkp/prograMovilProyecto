package com.ucb.perritos.login.domain.model

import com.ucb.perritos.features.login.domain.model.Token
import org.junit.Test
import org.junit.Assert.assertEquals

class TokenTest {

    @Test
    fun `token correcto se crea exitosamente`() {
        val tokenString = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" // Ejemplo JWT
        val token = Token(tokenString)
        assertEquals(tokenString, token.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el token esta vacio`() {
        Token("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el token es solo espacios`() {
        Token("   ")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el token es sospechosamente corto`() {
        Token("123")
    }
}