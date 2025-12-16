package com.ucb.perritos.registroMascota.domain.model

import com.ucb.perritos.features.registroMascota.domain.model.Raza
import org.junit.Test
import org.junit.Assert.assertEquals

class RazaTest {

    @Test
    fun `raza correcta se crea exitosamente`() {
        val raza = Raza("Pastor Aleman")
        assertEquals("Pastor Aleman", raza.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la raza esta vacia`() {
        Raza("   ")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la raza tiene caracteres invalidos`() {
        Raza("Pastor Aleman #1")
    }
}