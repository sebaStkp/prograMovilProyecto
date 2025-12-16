package com.ucb.perritos.features.registroMascota.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class RegistroPerroScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verificar_formulario_registro_mascota() {


        composeTestRule.setContent {


            var nombre by remember { mutableStateOf("") }
            var raza by remember { mutableStateOf("") }
            var edad by remember { mutableStateOf("") }
            var descripcion by remember { mutableStateOf("") }

            RegistroPerroScreenContent(
                nombrePerro = nombre,
                raza = raza,
                edad = edad,
                descripcion = descripcion,
                avatarUri = null,
                isLoading = false,
                onNombreChange = { nombre = it },
                onRazaChange = { raza = it },
                onEdadChange = { edad = it },
                onDescripcionChange = { descripcion = it },
                onPhotoClick = {},
                onRegistrarClick = {}
            )
        }


        composeTestRule.onNodeWithTag("tagFotoMascota").assertExists()


        composeTestRule.onNodeWithTag("tagNombrePerro")
            .performTextInput("Firulais")


        composeTestRule.onNodeWithTag("tagNombrePerro")
            .assertTextContains("Firulais")


        composeTestRule.onNodeWithTag("tagRaza")
            .performTextInput("Pastor Aleman")


        composeTestRule.onNodeWithTag("tagEdad")
            .performTextInput("5")


        composeTestRule.onNodeWithTag("tagDescripcion")
            .performTextInput("Es muy juguetón")


        composeTestRule.onNodeWithTag("tagBotonRegistrar")
            .performClick()
    }
}