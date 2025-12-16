package com.ucb.perritos.registroMascota.domain.model

import com.ucb.perritos.features.registroMascota.domain.model.Edad
import org.junit.Test
import org.junit.Assert.assertEquals

class EdadTest {

    @Test
    fun `edad correcta se crea exitosamente`() {
        val edad = Edad(5)
        assertEquals(5, edad.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la edad es negativa`() {
        Edad(-5)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la edad es irreal`() {
        Edad(50)
    }
}