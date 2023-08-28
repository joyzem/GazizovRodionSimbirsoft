package com.example.androidpractice.feature.auth

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.text.buildSpannedString
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.core.ui.navigation.findNavController
import com.example.androidpractice.core.ui.setOnEndDrawableClick
import com.example.androidpractice.core.ui.spans.ClickableText
import com.example.androidpractice.feature.auth.databinding.FragmentAuthBinding
import com.jakewharton.rxbinding4.widget.textChanges
import kotlinx.coroutines.launch

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
        with(binding) {
            // forgotPassword and signUp TextViews
            setSpannedStrings()
            inputPasswordEditText.setOnEndDrawableClick {
                viewModel.changePasswordVisibility()
            }
            loginButton.setOnClickListener {
                viewModel.loginButtonClicked()
            }
            authToolbar.setNavigationOnClickListener {
                // Intent to view model
                findNavController().onBackPressed()
            }
            inputEmailEditText.textChanges().skipInitialValue().subscribe {
                viewModel.setEmail(it.toString())
            }.addToComposite()
            inputPasswordEditText.textChanges().skipInitialValue().subscribe {
                viewModel.setPassword(it.toString())
            }.addToComposite()
        }
        observe()
    }

    private fun observe() {
        with(viewModel) {
            state.observe(viewLifecycleOwner) { state ->
                setPasswordVisibility(state.passwordHidden)
                setLoginButtonEnabled(state.loginButtonEnabled)
                setLoadingVisibility(state.loading)
            }
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    news.collect { news ->
                        when (news) {
                            AuthFeature.AuthNews.AuthSuccess -> {
                                // navigate
                                setFragmentResult(LOGIN_BUTTON_CLICKED, bundleOf())
                            }

                            else -> {
                                // handle other
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setLoadingVisibility(loading: Boolean) {
        binding.loginButton.text = getString(if (loading) R.string.loading else R.string.login)
    }

    private fun setLoginButtonEnabled(enabled: Boolean) {
        binding.loginButton.isEnabled = enabled
    }

    private fun setPasswordVisibility(hidden: Boolean) {
        val hidePasswordDrawable =
            ResourcesCompat.getDrawable(
                resources,
                com.example.androidpractice.core.designsystem.R.drawable.ic_eye_close,
                null
            )
        val showPasswordDrawable =
            ResourcesCompat.getDrawable(
                resources,
                com.example.androidpractice.core.designsystem.R.drawable.ic_eye_open,
                null
            )
        val (drawable, inputType) = if (hidden) {
            showPasswordDrawable to (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        } else {
            hidePasswordDrawable to (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
        }
        setDrawableAndInputTypeToPasswordTextField(drawable, inputType)
    }

    private fun setSpannedStrings() {
        with(binding) {
            forgotPasswordTextView.text = buildSpannedString {
                append(getString(R.string.forgot_password))
                setSpan(
                    ClickableText {
                        Toast.makeText(requireContext(), "content", Toast.LENGTH_SHORT).show()
                    },
                    0,
                    length,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            forgotPasswordTextView.movementMethod = LinkMovementMethod()
            registrationTextView.text = buildSpannedString {
                append(getString(R.string.signing_up))
                setSpan(
                    ClickableText {
                        Toast.makeText(requireContext(), "content", Toast.LENGTH_SHORT).show()
                    },
                    0,
                    length,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            registrationTextView.movementMethod = LinkMovementMethod()
        }
    }

    private fun setDrawableAndInputTypeToPasswordTextField(
        drawable: Drawable?,
        inputType: Int
    ) {
        binding.inputPasswordEditText.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                drawable,
                null
            )
            this.inputType = inputType
            setSelection(length())
            setTextAppearance(com.example.androidpractice.core.designsystem.R.style.Theme_AndroidPractice_TextInputEditText) // FIXME: the text font is strange if not to specify text appearance in code
        }
    }

    companion object {
        const val LOGIN_BUTTON_CLICKED = "login_button_clicked"

        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }
}
