package com.ucb.perritos.buscarMasctoa.domain.model

import com.ucb.perritos.features.buscarMascota.domain.model.Direccion

import org.junit.Test
import org.junit.Assert.assertEquals

class DireccionTest {

    @Test
    fun `direccion correcta se crea exitosamente`() {
        val texto = "Av. Heroinas esquina Ayacucho"
        val direccion = Direccion(texto)
        assertEquals(texto, direccion.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la direccion esta vacia`() {
        Direccion("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la direccion es solo espacios`() {
        Direccion("   ")
    }
}
