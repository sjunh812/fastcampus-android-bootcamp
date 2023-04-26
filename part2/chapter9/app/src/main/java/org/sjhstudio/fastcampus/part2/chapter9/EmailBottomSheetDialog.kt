package org.sjhstudio.fastcampus.part2.chapter9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.sjhstudio.fastcampus.part2.chapter9.databinding.BottomSheetDialogEmailBinding

class EmailBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialogEmailBinding

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
//        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
//        behavior.skipCollapsed = true
//        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun getTheme() = R.style.BottomSheetDialog
}