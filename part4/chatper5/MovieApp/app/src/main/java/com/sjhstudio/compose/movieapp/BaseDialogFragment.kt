package com.sjhstudio.compose.movieapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}