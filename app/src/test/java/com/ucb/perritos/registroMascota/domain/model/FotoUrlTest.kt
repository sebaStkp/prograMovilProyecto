package com.ucb.perritos.registroMascota.domain.model

import com.ucb.perritos.features.registroMascota.domain.model.FotoUrl

import org.junit.Test
import org.junit.Assert.assertEquals

class FotoUrlTest {

    @Test
    fun `url https se crea exitosamente`() {
        val url = FotoUrl("https://perritos.com/foto.jpg")
        assertEquals("https://perritos.com/foto.jpg", url.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la url no tiene protocolo`() {
        FotoUrl("www.google.com")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la url esta vacia`() {
        FotoUrl("")
    }
}