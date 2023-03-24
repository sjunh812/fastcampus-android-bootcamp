package org.sjhstudio.fastcampus.part2.chapter6.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter6.R
import org.sjhstudio.fastcampus.part2.chapter6.databinding.FragmentMyPageBinding
import org.sjhstudio.fastcampus.part2.chapter6.ui.LoginActivity

class MyPageFragment : Fragment(R.layout.fragment_my_page) {

    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPageBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnEdit.setOnClickListener {

            }

            btnLogout.setOnClickListener {
                Firebase.auth.signOut()
                navigateToLoginActivity()
            }
        }
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }
}