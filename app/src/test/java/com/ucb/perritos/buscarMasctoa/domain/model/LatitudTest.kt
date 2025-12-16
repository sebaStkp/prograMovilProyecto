package com.ucb.perritos.buscarMasctoa.domain.model

import com.ucb.perritos.features.buscarMascota.domain.model.Latitud

import org.junit.Test
import org.junit.Assert.assertEquals

class LatitudTest {

    @Test
    fun `latitud valida de Cochabamba se crea exitosamente`() {
        // Ejemplo real aprox de Cbba
        val valor = -17.39
        val latitud = Latitud(valor)
        assertEquals(valor, latitud.value, 0.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la latitud es menor a -90`() {
        Latitud(-90.1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la latitud es mayor a 90`() {
        Latitud(90.1)
    }
}