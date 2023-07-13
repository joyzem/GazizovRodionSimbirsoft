package com.example.androidpractice.screen.auth

import android.content.Context
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
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentAuthBinding
import com.example.androidpractice.di.ViewModelFactory
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.getAppComponent
import com.example.androidpractice.ui.navigation.findNavController
import com.example.androidpractice.ui.setOnEndDrawableClick
import com.example.androidpractice.ui.spans.ClickableText
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class AuthFragment : BaseFragment<FragmentAuthBinding>(
    0,
    FragmentAuthBinding::inflate,
    true
) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: AuthViewModel by viewModels {
        viewModelFactory
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().authSubcomponent().create().inject(this)
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
                setFragmentResult(LOGIN_BUTTON_CLICKED, bundleOf())
            }
            authToolbar.setNavigationOnClickListener {
                findNavController().onBackPressed()
            }
        }
        observe()
        setRxBinding()
    }

    private fun setRxBinding() {
        with(binding) {
            val loginButtonSubscription = Observable.combineLatest(
                inputEmailEditText.textChanges().skipInitialValue(),
                inputPasswordEditText.textChanges().skipInitialValue()
            ) { email, password ->
                return@combineLatest password.length >= 6 && email.length >= 6
            }.subscribe { enabled ->
                viewModel.setLoginButtonEnabled(enabled)
            }
            compositeDisposable.add(loginButtonSubscription)
        }
    }

    private fun observe() {
        val hidePasswordDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_eye_close, null)
        val showPasswordDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_eye_open, null)

        with(viewModel) {
            passwordIsHidden.observe(viewLifecycleOwner) { passwordIsHidden ->
                val (drawable, inputType) = if (passwordIsHidden) {
                    showPasswordDrawable to (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                } else {
                    hidePasswordDrawable to (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                }
                setDrawableAndInputTypeToPasswordTextField(drawable, inputType)
            }

            isLoginButtonEnabled.observe(viewLifecycleOwner) { enabled ->
                binding.loginButton.isEnabled = enabled
            }
        }
    }

    private fun setSpannedStrings() {
        with(binding) {
            forgotPasswordTextView.text = buildSpannedString {
                append(getString(R.string.forgot_password))
                setSpan(
                    ClickableText {
                        Toast.makeText(requireContext(), "content", Toast.LENGTH_SHORT).show()
                    }, 0, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            forgotPasswordTextView.movementMethod = LinkMovementMethod()
            registrationTextView.text = buildSpannedString {
                append(getString(R.string.signing_up))
                setSpan(
                    ClickableText {
                        Toast.makeText(requireContext(), "content", Toast.LENGTH_SHORT).show()
                    }, 0, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
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
            setTextAppearance(R.style.Theme_AndroidPractice_TextInputEditText) // FIXME: the text font is strange if not to specify text appearance in code
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    companion object {
        const val LOGIN_BUTTON_CLICKED = "login_button_clicked"

        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }
}
