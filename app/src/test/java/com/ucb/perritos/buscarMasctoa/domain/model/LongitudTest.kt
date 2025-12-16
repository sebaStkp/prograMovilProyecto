package com.ucb.perritos.buscarMasctoa.domain.model

import com.ucb.perritos.features.buscarMascota.domain.model.Longitud

import org.junit.Test
import org.junit.Assert.assertEquals

class LongitudTest {

    @Test
    fun `longitud valida de Cochabamba se crea exitosamente`() {
        // Ejemplo real aprox de Cbba
        val valor = -66.15
        val longitud = Longitud(valor)
        assertEquals(valor, longitud.value, 0.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la longitud es menor a -180`() {
        Longitud(-180.1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la longitud es mayor a 180`() {
        Longitud(180.1)
    }
}