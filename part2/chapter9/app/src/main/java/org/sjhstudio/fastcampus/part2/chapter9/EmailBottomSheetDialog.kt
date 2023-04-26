package org.sjhstudio.fastcampus.part2.chapter9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import org.sjhstudio.fastcampus.part2.chapter9.databinding.BottomSheetDialogEmailBinding
import org.sjhstudio.fastcampus.part2.chapter9.util.Validation

class EmailBottomSheetDialog : BottomSheetDialogFragment() {

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
                    when (Validation.validateEmail(email.toString())) {
                        true -> handleInputSuccess(textInputLayoutEmail)
                        false -> handleInputError(textInputLayoutEmail, "이메일 형식이 올바르지 않습니다.")
                        null -> handleInputError(textInputLayoutEmail, "이메일을 입력해주세요.")
                    }
                }
            }
            btnOk.setOnClickListener {
                // LoginActivity 에 이메일 전달
                dismiss()
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