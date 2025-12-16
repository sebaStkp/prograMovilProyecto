package com.ucb.perritos.features.login.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verificar_flujo_login_visual() {
        composeTestRule.setContent {
            LoginScreenContent(
                email = "",
                password = "",
                onEmailChange = {},
                onPasswordChange = {},
                onLoginClick = {},
                isLoading = false,
                irRegistroCuenta = {}
            )
        }

        composeTestRule.onNodeWithTag("tagEmail")
            .performTextInput("juan@gmail.com")

        composeTestRule.onNodeWithTag("tagPassword")
            .performTextInput("123456")

        composeTestRule.onNodeWithTag("tagBotonLogin")
            .assertExists()
            .performClick()
    }
}