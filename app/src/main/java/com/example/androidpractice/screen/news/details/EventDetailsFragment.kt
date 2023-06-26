package com.example.androidpractice.screen.news.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.text.buildSpannedString
import androidx.core.text.toSpannable
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentEventDetailsBinding
import com.example.androidpractice.di.ViewModelsFactoryOwner
import com.example.androidpractice.di.getViewModel
import com.example.androidpractice.screen.news.NewsViewModel
import com.example.androidpractice.screen.news.getEventDateText
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.PhoneNumberSpan
import com.example.androidpractice.ui.navigation.findNavController

class EventDetailsFragment : BaseFragment(R.id.newsNavItem, true) {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ViewModelsFactoryOwner).getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventId: String = arguments?.getString(EVENT_ID) ?: ""
        val event = viewModel.getEventById(eventId = eventId) ?: return
        binding.apply {
            toolbar.title = event.title
            eventTitleTextView.text = event.title
            eventDateTextView.text = getEventDateText(binding.root, event.dates)
            sponsorTextView.text = event.sponsor
            addressTextView.text = event.address
            phonesTextView.text = getSpannablePhoneNumbers(event.phoneNumbers)
            phonesTextView.movementMethod = LinkMovementMethod()
            emailTextView.text = getSpannableMail(event.email)
            emailTextView.movementMethod = LinkMovementMethod()
            descriptionTextView.text = event.description
            siteTextView.text = getSpannableSite(event.siteUrl)
            siteTextView.movementMethod = LinkMovementMethod()
            toolbar.setNavigationOnClickListener {
                findNavController().onBackPressed()
            }
        }
    }

    private fun getSpannableSite(siteUrl: String): Spannable {
        return buildSpannedString {
            append(getString(R.string.go_to_organization_site))
            val siteSpan = object : ClickableSpan() {

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = requireContext().getColor(R.color.leaf)
                    ds.isUnderlineText = true
                }

                override fun onClick(p0: View) {
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
                    ds.color = requireContext().getColor(R.color.leaf)
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

    private fun getSpannablePhoneNumbers(phoneNumbers: List<String>): Spannable {
        return buildSpannedString {
            var currentIndex = 0
            phoneNumbers.dropLast(1).forEach {
                appendLine(it)
                setSpan(
                    PhoneNumberSpan(it),
                    currentIndex,
                    length,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
                currentIndex += it.length
            }
            append(phoneNumbers.last())
            setSpan(
                PhoneNumberSpan(phoneNumbers.last()),
                currentIndex,
                length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }.toSpannable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EVENT_ID = "event_id"
        fun newInstance(eventId: String): EventDetailsFragment {
            return EventDetailsFragment().apply {
                arguments = bundleOf(
                    EVENT_ID to eventId
                )
            }
        }
    }
}