package com.example.androidpractice.screen.profile

import android.app.Dialog
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.androidpractice.R
import java.io.Serializable

class PhotoDialogFragment : DialogFragment(), Serializable {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val inflater = requireActivity().layoutInflater
            val builder = AlertDialog.Builder(activity)
            val layout = inflater.inflate(R.layout.photo_dialog, null)

            layout.findViewById<TextView>(R.id.choosePhotoTextView)
                .setOnClickListener {
                    setFragmentResult(
                        RESULT_KEY,
                        bundleOf(ACTION_KEY to ACTION_CHOOSE,"dialog" to this)
                    )
                    dismiss()
                }
            layout.findViewById<TextView>(R.id.takePhotoTextView)
                .setOnClickListener {
                    setFragmentResult(
                        RESULT_KEY,
                        bundleOf(ACTION_KEY to ACTION_TAKE)
                    )
                    dismiss()
                }
            layout.findViewById<TextView>(R.id.deletePhotoTextView)
                .setOnClickListener {
                    setFragmentResult(
                        RESULT_KEY,
                        bundleOf(ACTION_KEY to ACTION_DELETE, "dialog" to this)
                    )
                    dismiss()
                }
            builder.setView(layout)
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
