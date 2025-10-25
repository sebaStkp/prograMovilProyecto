package com.ucb.perritos.features.perfildueno

import com.ucb.perritos.features.perfildueno.data.database.InMemoryPerfilDuenoDao
import com.ucb.perritos.features.perfildueno.data.database.PerfilDuenoEntity
import com.ucb.perritos.features.perfildueno.data.datasource.PerfilDuenoLocalDataSource
import com.ucb.perritos.features.perfildueno.data.mapper.PerfilDuenoMapper
import com.ucb.perritos.features.perfildueno.data.repository.PerfilDuenoRepository
import com.ucb.perritos.features.perfildueno.domain.usecase.ObtenerPerfilDuenoUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ObtenerPerfilDuenoIntegrationTest {

    @Test
    fun obtenerPerfil_desdeInMemoryDao_devuelvePerfilEsperado() = runBlocking {
        // Arrange: dao en memoria con un perfil guardado
        val dao = InMemoryPerfilDuenoDao()
        val entidad = PerfilDuenoEntity(
            id = "owner",
            nombre = "Ana",
            email = "ana@example.com",
            telefono = "987654321"
        )
        dao.insertarPerfil(entidad)

        val local = PerfilDuenoLocalDataSource(dao)
        val mapper = PerfilDuenoMapper()
        val repo = PerfilDuenoRepository(local, mapper)
        val useCase = ObtenerPerfilDuenoUseCase(repo)

        // Act
        val perfil = useCase()

        // Assert
        assertNotNull("El perfil no debe ser null", perfil)
        assertEquals("Ana", perfil?.nombre)
        assertEquals("ana@example.com", perfil?.email)
        assertEquals("987654321", perfil?.telefono)
    }
}

