package com.ucb.perritos.registroMascota.domain.model

import com.ucb.perritos.features.registroMascota.domain.model.Descripcion

import org.junit.Test
import org.junit.Assert.assertEquals

class DescripcionTest {

    @Test
    fun `descripcion correcta se crea exitosamente`() {
        val texto = "Es un perro muy juguetón y le gusta correr."
        val descripcion = Descripcion(texto)
        assertEquals(texto, descripcion.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la descripcion esta vacia`() {
        Descripcion("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la descripcion es solo espacios`() {
        Descripcion("   ")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `falla si la descripcion es demasiado larga`() {
        // Creamos un texto de 251 caracteres
        val textoLargo = "A".repeat(251)
        Descripcion(textoLargo)
    }
}