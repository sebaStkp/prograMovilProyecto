package com.ucb.perritos.login.domain.model

import com.ucb.perritos.features.login.domain.model.Nickname

import org.junit.Test
import org.junit.Assert.assertEquals

class NicknameTest {

    @Test
    fun `nickname correcto se crea exitosamente`() {
        val user = "JuanPablo99"
        val nickname = Nickname(user)
        assertEquals(user, nickname.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el nickname esta vacio`() {
        Nickname("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el nickname es muy corto`() {
        Nickname("Jo") // Menos de 3 caracteres
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el nickname es demasiado largo`() {
        val largo = "A".repeat(51)
        Nickname(largo)
    }
}