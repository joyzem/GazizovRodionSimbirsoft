package com.example.androidpractice.screen.auth

import com.example.androidpractice.domain.auth.AuthInteractor
import com.example.androidpractice.mvi.AsyncFeature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthFeature @Inject constructor(
    private val authInteractor: AuthInteractor
) : AsyncFeature<AuthFeature.AuthState, AuthFeature.AuthWish, AuthFeature.AuthNews> {

    private val _state = MutableStateFlow(AuthState())
    override val state: StateFlow<AuthState> = _state.asStateFlow()
    private val _news: MutableSharedFlow<AuthNews> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val news: SharedFlow<AuthNews> = _news.asSharedFlow()

    override fun newWish(scope: CoroutineScope, wish: AuthWish) {
        when (wish) {
            is AuthWish.EmailInput -> handleEmailInput(wish)
            is AuthWish.PasswordInput -> handlePasswordInput(wish)
            is AuthWish.PasswordVisibility -> handlePasswordVisibility()
            is AuthWish.LogIn -> handleLogin(scope)
            else -> {
                // other
            }
        }
    }

    private fun isLoginButtonEnabled(email: String, password: String): Boolean {
        return email.length >= authInteractor.minEmailLength && password.length >= authInteractor.minPasswordLength
    }

    private fun handleEmailInput(intent: AuthWish.EmailInput) {
        _state.update {
            it.copy(
                email = intent.email,
                loginButtonEnabled = isLoginButtonEnabled(intent.email, it.password)
            )
        }
    }

    private fun handlePasswordInput(intent: AuthWish.PasswordInput) {
        _state.update {
            it.copy(
                password = intent.password,
                loginButtonEnabled = isLoginButtonEnabled(it.email, intent.password)
            )
        }
    }

    private fun handlePasswordVisibility() {
        _state.update {
            it.copy(passwordHidden = !it.passwordHidden)
        }
    }

    private fun handleLogin(scope: CoroutineScope) {
        scope.launch {
            _state.update {
                it.copy(loading = true)
            }
            withContext(Dispatchers.IO) {
                val token = authInteractor.login(state.value.email, state.value.password)
                _news.tryEmit(
                    token?.let { AuthNews.AuthSuccess } ?: AuthNews.AuthFailure(404)
                )
            }
            _state.update {
                it.copy(loading = false)
            }
        }
    }

    data class AuthState(
        val email: String = "",
        val password: String = "",
        val passwordHidden: Boolean = true,
        val loginButtonEnabled: Boolean = false,
        val loading: Boolean = false
    )

    sealed interface AuthWish {
        data class EmailInput(val email: String) : AuthWish
        data class PasswordInput(val password: String) : AuthWish
        object PasswordVisibility : AuthWish
        object LogIn : AuthWish
        object ForgotPassword : AuthWish
        object SignUp : AuthWish
    }

    sealed interface AuthNews {
        object Nothing : AuthNews
        object AuthSuccess : AuthNews
        object ForgotPasswordClicked : AuthNews
        object SignUpClicked : AuthNews
        data class AuthFailure(val code: Int) : AuthNews
    }
}