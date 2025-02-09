package com.sjhstudio.compose.movieapp.features.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sjhstudio.compose.movieapp.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoDialogFragment : BaseDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}