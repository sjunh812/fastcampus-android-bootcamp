package org.sjhstudio.fastcampus.part2.chapter9

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import org.sjhstudio.fastcampus.part2.chapter9.databinding.DialogProgressBinding

class ProgressDialog : DialogFragment() {

    private var _binding: DialogProgressBinding? = null
    val binding: DialogProgressBinding
        get() = _binding ?: error("ViewBinding Error")

    companion object {
        fun newInstance(): ProgressDialog {
            return ProgressDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogProgressBinding.inflate(layoutInflater)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().run {
            add(this@ProgressDialog, tag)
            commitAllowingStateLoss()
        }
    }
}