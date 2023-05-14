package org.sjhstudio.fastcampus.part2.chapter9.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import org.sjhstudio.fastcampus.part2.chapter9.R
import org.sjhstudio.fastcampus.part2.chapter9.databinding.BottomSheetDialogEmailBinding
import org.sjhstudio.fastcampus.part2.chapter9.util.Validation

class EmailBottomSheetDialog(
    private val submitEmail: (String) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialogEmailBinding

    override fun getTheme() = R.style.BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetDialogEmailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
//        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
//        behavior.skipCollapsed = true
//        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initViews() {
        with(binding) {
            etEmail.addTextChangedListener { text ->
                text?.let { email ->
                    validateEmail(email.toString())
                }
            }
            btnOk.setOnClickListener {
                // LoginActivity 에 이메일 전달
                if (validateEmail(etEmail.toString())) {
                    submitEmail.invoke(etEmail.toString())
                    dismiss()
                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return when (Validation.validateEmail(email)) {
            true -> {
                handleInputSuccess(binding.textInputLayoutEmail)
                true
            }
            false -> {
                handleInputError(binding.textInputLayoutEmail, "이메일 형식이 올바르지 않습니다.")
                false
            }
            null -> {
                handleInputError(binding.textInputLayoutEmail, "이메일을 입력해주세요.")
                false
            }
        }
    }

    private fun handleInputSuccess(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }

    private fun handleInputError(textInputLayout: TextInputLayout, error: String) {
        textInputLayout.error = error
    }
}