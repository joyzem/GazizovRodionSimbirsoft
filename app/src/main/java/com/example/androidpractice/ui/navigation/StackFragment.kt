package com.example.androidpractice.ui.navigation

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment


/**
 * Stack fragment is used by [NavController] to navigate with FragmentManager using class of the fragment
 */
data class StackFragment(
    val fragmentClassName: String,
    val args: Bundle? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readBundle(Bundle::class.java.classLoader)
    ) {
    }

    init {
        Log.i("StackFragment", "StackFragment class name: $fragmentClassName")
    }

    fun instantiateFragment(): Fragment? {
        return try {
            val fragmentClass = Class.forName(fragmentClassName)
            fragmentClass.newInstance() as? Fragment
        } catch (e: Exception) {
            null
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fragmentClassName)
        parcel.writeBundle(args)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StackFragment> {
        override fun createFromParcel(parcel: Parcel): StackFragment {
            return StackFragment(parcel)
        }

        override fun newArray(size: Int): Array<StackFragment?> {
            return arrayOfNulls(size)
        }
    }
}