package com.loanmanagementapp.features.login.ui

import app.cash.turbine.test
import com.loanmanagementapp.common.domain.TextFieldValidator
import com.loanmanagementapp.common.ui.component.LoanManagementTextFieldState
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @MockK
    private lateinit var validator: TextFieldValidator<String>
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = LoginViewModel(validator)
    }

    @Test
    fun initial_state_is_correct() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("", state.email)
            assertEquals("", state.password)
            assertEquals(false, state.isLoading)
            assertEquals(false, state.isValidEmail)
            assertEquals(validator, state.validator)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun onEmailStateChanged_updates_email_and_isValidEmail() = runTest {
        val textState = LoanManagementTextFieldState(text = "test@test.com", isError = false)
        viewModel.onEmailStateChanged(textState)
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("test@test.com", state.email)
            assertTrue(state.isValidEmail)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun onEmailStateChanged_withInvalidEmail_sets_isValidEmail_false() = runTest {
        val textState = LoanManagementTextFieldState(text = "notanemail", isError = true)
        viewModel.onEmailStateChanged(textState)
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("notanemail", state.email)
            assertEquals(false, state.isValidEmail)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun onPasswordChanged_updates_password() = runTest {
        val password = "secret123"
        viewModel.onPasswordChanged(password)
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(password, state.password)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
