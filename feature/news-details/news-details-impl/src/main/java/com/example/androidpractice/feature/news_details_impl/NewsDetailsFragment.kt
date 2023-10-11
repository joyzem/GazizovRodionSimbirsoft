package com.example.androidpractice.feature.news_details_impl

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract.Instances.EVENT_ID
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.app.PendingIntentCompat
import androidx.core.os.bundleOf
import androidx.core.text.buildSpannedString
import androidx.core.text.toSpannable
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.androidpractice.core.model.event.Event
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.core.ui.navigation.findNavController
import com.example.androidpractice.core.ui.spans.PhoneNumberSpan
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi.Companion.EVENT_ID_KEY
import com.example.androidpractice.feature.news_details_impl.databinding.FragmentEventDetailsBinding
import com.example.androidpractice.feature.news_details_impl.money.DonationDialog
import com.example.androidpractice.feature.news_details_impl.utils.getEventDateText
import com.example.androidpractice.core.designsystem.R as designR

class NewsDetailsFragment : BaseFragment<FragmentEventDetailsBinding, NewsDetailsViewModel>(
    com.example.androidpractice.core.ui.R.id.newsNavItem,
    FragmentEventDetailsBinding::inflate,
    true
) {
    override val viewModel: NewsDetailsViewModel by viewModels {
        viewModelFactory
    }

    override fun injectViewModelFactory() {
        ViewModelProvider(this).get<NewsDetailsComponentViewModel>()
            .newsDetailsComponent.inject(this)
    }

    private val eventId by lazy {
        checkNotNull(arguments?.getString(EVENT_ID))
    }
    private val eventName by lazy {
        checkNotNull(viewModel.events.value?.find { it.id == eventId }?.title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.setNavigationOnClickListener {
                if (!findNavController().onBackPressed()) requireActivity().finish()
            }
            donationTextView.setOnClickListener {
                DonationDialog.getInstance(eventId, eventName).show(childFragmentManager, null)
            }
        }

        observe()
    }

    private fun observe() {
        viewModel.events.observe(viewLifecycleOwner) { events ->
            if (events != null) {
                val event = events.find {
                    it.id == eventId
                }
                event?.let {
                    setEvent(it)
                    viewModel.readEvent(eventId)
                } ?: throw IllegalStateException()
            }
        }
    }

    private fun setEvent(event: Event) {
        binding.apply {
            toolbar.title = event.title
            eventTitleTextView.text = event.title
            eventDateTextView.text = getEventDateText(binding.root, event.startDate, event.endDate)
            sponsorTextView.text = event.sponsor
            addressTextView.text = event.address
            phonesTextView.text = getSpannablePhoneNumbers(event.phoneNumber)
            phonesTextView.movementMethod = LinkMovementMethod()
            emailTextView.text = getSpannableMail(event.email)
            emailTextView.movementMethod = LinkMovementMethod()
            descriptionTextView.text = event.description
            siteTextView.text = getSpannableSite(event.siteUrl)
            siteTextView.movementMethod = LinkMovementMethod()
        }
    }

    private fun getSpannableSite(siteUrl: String): Spannable {
        return buildSpannedString {
            append(getString(R.string.go_to_organization_site))
            val siteSpan = object : ClickableSpan() {

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = requireContext().getColor(designR.color.leaf)
                    ds.isUnderlineText = true
                }

                override fun onClick(v: View) {
                    val webpage: Uri = Uri.parse(siteUrl)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    if (intent.resolveActivity(requireContext().packageManager) != null) {
                        startActivity(intent)
                    }
                }
            }
            setSpan(siteSpan, 0, length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        }.toSpannable()
    }

    private fun getSpannableMail(email: String): Spannable {
        return buildSpannedString {
            append(getString(R.string.have_questions))
            append(" ")
            val emailSpan = object : ClickableSpan() {

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = requireContext().getColor(designR.color.leaf)
                    ds.isUnderlineText = true
                }

                override fun onClick(p0: View) {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra(Intent.EXTRA_EMAIL, email)
                    if (intent.resolveActivity(requireContext().packageManager) != null) {
                        requireContext().startActivity(intent)
                    }
                }
            }
            val cursor = length
            append(getString(R.string.write_us))
            setSpan(emailSpan, cursor, length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        }.toSpannable()
    }

    private fun getSpannablePhoneNumbers(phoneNumbers: String): Spannable {
        return buildSpannedString {
            append(phoneNumbers)
            setSpan(
                PhoneNumberSpan(phoneNumbers),
                0,
                length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }.toSpannable()
    }

    companion object {
        fun newInstance(eventId: String): NewsDetailsFragment {
            return NewsDetailsFragment().apply {
                arguments = bundleOf(
                    EVENT_ID_KEY to eventId
                )
            }
        }

        fun getScreenDeepLinkIntent(context: Context, eventId: String) = PendingIntentCompat.getActivity(
            context,
            0,
            Intent().apply {
                component = ComponentName(
                    "com.example.androidpractice",
                    "com.example.androidpractice.MainActivity"
                )
                putExtra(EVENT_ID_KEY, eventId)
            },
            PendingIntent.FLAG_ONE_SHOT,
            false
        )
    }
}
