package com.example.androidpractice.feature.auth

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.androidpractice.core.designsystem.theme.AppTheme
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.feature.auth.databinding.FragmentAuthBinding

class AuthFragment : BaseFragment<FragmentAuthBinding, AuthViewModel>(
    0,
    FragmentAuthBinding::inflate,
    true
) {
    override val viewModel: AuthViewModel by viewModels {
        viewModelFactory
    }

    override fun injectViewModelFactory() {
        ViewModelProvider(this).get<AuthComponentViewModel>().authComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.authComposable) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            setContent {
                AppTheme {
                    val state by viewModel.state.collectAsState()
                    val news by viewModel.news.collectAsState(initial = AuthFeature.AuthNews.Nothing)
                    when (news) {
                        AuthFeature.AuthNews.AuthSuccess -> {
                            setFragmentResult(LOGIN_BUTTON_CLICKED, bundleOf())
                        }

                        else -> {
                            // Handle error
                        }
                    }
                    val email by remember { derivedStateOf { state.email } }
                    val password by remember { derivedStateOf { state.password } }
                    val loading by remember { derivedStateOf { state.loading } }
                    val isPasswordHidden by remember { derivedStateOf { state.passwordHidden } }
                    val isLoginButtonEnabled by remember { derivedStateOf { state.loginButtonEnabled } }
                    AuthScreen(
                        email = email,
                        onEmailInput = viewModel::setEmail,
                        password = password,
                        onPasswordInput = viewModel::setPassword,
                        isPasswordHidden = isPasswordHidden,
                        onChangePasswordVisibility = viewModel::changePasswordVisibility,
                        isLoginButtonActive = isLoginButtonEnabled,
                        loading = loading,
                        onLoginButtonClick = viewModel::loginButtonClicked,
                        onForgotPasswordClick = { },
                        onSignUpClick = { },
                        onNavigateUp = {
                            activity?.finish()
                        }
                    )
                }
            }
        }
    }

    companion object {
        const val LOGIN_BUTTON_CLICKED = "login_button_clicked"

        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }
}
