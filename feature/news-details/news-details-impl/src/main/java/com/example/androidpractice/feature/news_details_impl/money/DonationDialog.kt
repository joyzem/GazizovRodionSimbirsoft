package com.example.androidpractice.feature.news_details_impl.money

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.androidpractice.core.ui.edittext.MoneyTextWatcher
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi.Companion.EVENT_ID_KEY
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi.Companion.EVENT_NAME_KEY
import com.example.androidpractice.feature.news_details_impl.NewsDetailsComponentViewModel
import com.example.androidpractice.feature.news_details_impl.databinding.DialogDonationBinding
import com.example.androidpractice.feature.news_details_impl.money.components.DonationOptionPicker
import com.example.androidpractice.feature.news_details_impl.workers.DonationWorker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

internal class DonationDialog : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: DialogDonationBinding? = null
    private val binding get() = _binding!!

    private val registerNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                // Show explanation dialog
            }
            launchPaymentWorker()
        }

    private val eventId by lazy {
        requireArguments().getString(EVENT_ID_KEY)
    }
    private val eventName by lazy {
        requireArguments().getString(EVENT_NAME_KEY)
    }

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
            val builder = createBuilder(activity)
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
                transferButton.setOnClickListener {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (checkNotificationPermissionGranted()) {
                            launchPaymentWorker()
                        } else {
                            registerNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    } else {
                        launchPaymentWorker()
                    }
                }

                setTextWatchersOnDonationEditText(savedInstanceState)
            }

            observeValidInput()

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException()
    }

    private fun observeValidInput() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.isInputValid.collectLatest { valid ->
                    binding.transferButton.isEnabled = valid
                }
            }
        }
    }

    private fun setTextWatchersOnDonationEditText(savedInstanceState: Bundle?) {
        with(binding) {
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
        }
    }

    private fun checkNotificationPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun launchPaymentWorker() {
        val workRequest = OneTimeWorkRequestBuilder<DonationWorker>()
            .setInputData(
                DonationWorker.createInputData(
                    checkNotNull(eventId),
                    checkNotNull(eventName),
                    viewModel.selectedOption.value
                )
            )
            .setConstraints(Constraints(requiresCharging = true))
            .build()
        WorkManager.getInstance(requireContext()).enqueue(workRequest)
        dismiss()
    }

    private fun createBuilder(activity: Context): AlertDialog.Builder {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        _binding = DialogDonationBinding.inflate(inflater)
        return builder
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

        fun getInstance(
            eventId: String,
            eventName: String
        ) = DonationDialog().apply {
            arguments = bundleOf(
                EVENT_ID_KEY to eventId,
                EVENT_NAME_KEY to eventName
            )
        }
    }
}
