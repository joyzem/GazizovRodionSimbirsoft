package com.example.androidpractice.screen.profile

import android.app.Dialog
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.androidpractice.R

class PhotoDialogFragment : DialogFragment() {

    private lateinit var listener: PhotoDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val inflater = requireActivity().layoutInflater
            val builder = AlertDialog.Builder(activity)
            val layout = inflater.inflate(R.layout.photo_dialog, null)

            layout.findViewById<LinearLayout>(R.id.choosePhotoOption)
                .setOnClickListener {
                    listener.onChoosePhotoClicked(this)
                }
            layout.findViewById<LinearLayout>(R.id.takePhotoOption)
                .setOnClickListener {
                    listener.onTakePhotoClicked(this)
                }
            layout.findViewById<LinearLayout>(R.id.deletePhotoOption)
                .setOnClickListener {
                    listener.onDeletePhotoClicked(this)
                }

            builder.setView(layout)
            builder.create()
        } ?: throw IllegalStateException()
    }

    interface PhotoDialogListener {
        fun onChoosePhotoClicked(dialog: DialogFragment)
        fun onTakePhotoClicked(dialog: DialogFragment)
        fun onDeletePhotoClicked(dialog: DialogFragment)
    }

    companion object {
        fun newInstance(listener: PhotoDialogListener): PhotoDialogFragment {
            val dialog = PhotoDialogFragment().apply {
                this.listener = listener
            }
            return dialog
        }
    }
}
