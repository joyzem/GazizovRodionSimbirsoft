package com.example.androidpractice.feature.news_details_impl.money

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.androidpractice.feature.news_details_impl.NewsDetailsComponentViewModel
import com.example.androidpractice.feature.news_details_impl.databinding.DialogDonationBinding
import com.example.androidpractice.feature.news_details_impl.money.components.DonationOptionPicker
import com.example.androidpractice.feature.news_details_impl.utils.MoneyTextWatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

class DonationDialog : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: DialogDonationBinding? = null
    val binding get() = _binding!!

    private val viewModel by viewModels<DonationViewModel> {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<NewsDetailsComponentViewModel>().newsDetailsComponent.inject(
            this
        )
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = requireActivity().layoutInflater
            _binding = DialogDonationBinding.inflate(inflater)
            with(binding) {
                moneyPickerContainer.setContent {
                    val selectedOption by viewModel.selectedOption.collectAsState()
                    val moneyHelpOptions by viewModel.possibleHelpOptions.collectAsState()
                    DonationOptionPicker(
                        selectedOption = selectedOption,
                        possibleOptions = moneyHelpOptions,
                        onOptionSelected = { money ->
                            suggestedMoneyEditText.setText("")
                            viewModel.selectOption(money)
                        }
                    )
                }
                cancelButton.setOnClickListener {
                    dismiss()
                }

                val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("ru", "RU")).apply {
                    currency = Currency.getInstance("RUB")
                    maximumFractionDigits = 0
                }
                suggestedMoneyEditText.addTextChangedListener(
                    MoneyTextWatcher(
                        suggestedMoneyEditText,
                        currencyFormatter
                    )
                )
                suggestedMoneyEditText.doAfterTextChanged { editable ->
                    if (savedInstanceState?.getString(INPUT_MONEY) != null) {
                        savedInstanceState.remove(INPUT_MONEY)
                        return@doAfterTextChanged
                    }
                    try {
                        currencyFormatter.parse(editable?.toString() ?: "")?.toInt()?.let { money ->
                            viewModel.selectOption(money)
                        } ?: viewModel.selectOption(0)
                    } catch (e: Exception) {
                        editable?.toString()?.toIntOrNull()?.let { money ->
                            viewModel.selectOption(money)
                        } ?: viewModel.selectOption(0)
                    }
                }
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.RESUMED) {
                        viewModel.isInputValid.collectLatest { valid ->
                            transferButton.isEnabled = valid
                        }
                    }
                }
            }
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INPUT_MONEY, binding.suggestedMoneyEditText.text?.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val INPUT_MONEY = "input_money"
    }
}
