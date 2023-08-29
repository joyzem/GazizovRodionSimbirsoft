package com.example.androidpractice.feature.profile

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.androidpractice.feature.profile.databinding.PhotoDialogBinding
import java.io.Serializable

internal class PhotoDialogFragment : DialogFragment(), Serializable {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = requireActivity().layoutInflater
            val binding = PhotoDialogBinding.inflate(inflater)
            with(binding) {
                choosePhotoTextView.setOnClickListener {
                    setFragmentResult(
                        RESULT_KEY,
                        bundleOf(ACTION_KEY to ACTION_CHOOSE)
                    )
                    dismiss()
                }
                takePhotoTextView.setOnClickListener {
                    setFragmentResult(
                        RESULT_KEY,
                        bundleOf(ACTION_KEY to ACTION_TAKE)
                    )
                    dismiss()
                }
                deletePhotoTextView
                    .setOnClickListener {
                        setFragmentResult(
                            RESULT_KEY,
                            bundleOf(ACTION_KEY to ACTION_DELETE)
                        )
                        dismiss()
                    }
            }
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException()
    }

    companion object {
        const val ACTION_CHOOSE = "choose"
        const val ACTION_DELETE = "delete"
        const val ACTION_TAKE = "take"
        const val ACTION_KEY = "action"
        const val RESULT_KEY = "photo_dialog"

        fun newInstance(): PhotoDialogFragment {
            return PhotoDialogFragment()
        }
    }
}
