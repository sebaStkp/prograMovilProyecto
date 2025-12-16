package com.ucb.perritos.registroMascota.domain.model

import com.ucb.perritos.features.registroMascota.domain.model.NombrePerro
import org.junit.Test
import org.junit.Assert.assertEquals

class NombrePerroTest {

    @Test
    fun `nombre correcto se crea exitosamente`() {
        val nombre = NombrePerro("Firulais")
        assertEquals("Firulais", nombre.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el nombre esta vacio`() {
        NombrePerro("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el nombre tiene numeros`() {
        NombrePerro("Firulais 2") // El número dispara el error
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si el nombre es demasiado largo`() {
        val nombreLargo = "A".repeat(31)
        NombrePerro(nombreLargo)
    }
}