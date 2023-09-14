package com.example.androidpractice.feature.news_details_impl.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference
import java.text.NumberFormat

class MoneyTextWatcher(editText: EditText, private val currencyFormatter: NumberFormat) :
    TextWatcher {

    private val editTextWR = WeakReference(editText)

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        editTextWR.get()?.let { editText ->
            editText.removeTextChangedListener(this)
            val parsedMoney = try {
                currencyFormatter.parse(text?.toString() ?: "")?.toInt()
            } catch (e: Exception) {
                text?.toString()?.toIntOrNull()
            }
            parsedMoney?.let { money ->
                val formattedMoneyString = currencyFormatter.format(money)
                editText.setText(formattedMoneyString)
                editText.setSelection(
                    findPositionForSelection(
                        formattedMoneyString
                    )
                )
            } ?: editText.setText("")
            editText.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    private fun findPositionForSelection(money: String): Int {
        return money.length - 2 // Only rubles
    }
}