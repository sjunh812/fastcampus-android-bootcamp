package org.sjhstudio.fastcampus.part2.chapter6.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter6.databinding.FragmentMyPageBinding
import org.sjhstudio.fastcampus.part2.chapter6.model.User
import org.sjhstudio.fastcampus.part2.chapter6.ui.base.BaseFragment
import org.sjhstudio.fastcampus.part2.chapter6.ui.LoginActivity
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_USERS
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_USERS_DESCRIPTION
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_USERS_USER_NAME
import org.sjhstudio.fastcampus.part2.chapter6.util.Validation
import org.sjhstudio.fastcampus.part2.chapter6.util.showToastMessage

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    companion object {
        private const val LOG = "MyPageFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            getUser { user ->
                etNickname.setText(user.userName)
                etDescription.setText(user.description)
            }

            btnEdit.setOnClickListener {
                val nickname = etNickname.text.toString()
                val description = etDescription.text.toString()

                if (!nicknameValidation(nickname)) return@setOnClickListener

                val map = mutableMapOf<String, Any>(
                    DB_USERS_USER_NAME to nickname,
                    DB_USERS_DESCRIPTION to description
                )

                updateUser(map)
            }

            btnLogout.setOnClickListener {
                Firebase.auth.signOut()
                navigateToLoginActivity()
            }
        }
    }

    private fun getUser(updateUi: (User) -> Unit) {
        database.child(DB_USERS).child(userId!!)
            .get()
            .addOnSuccessListener { dataSnapshot ->
                val user = dataSnapshot.getValue(User::class.java)
                    ?: return@addOnSuccessListener
                updateUi.invoke(user)
            }
    }

    private fun updateUser(userMap: Map<String, Any>) {
        database.child(DB_USERS).child(userId!!)
            .updateChildren(userMap)
            .addOnSuccessListener {
                requireContext().showToastMessage("수정되었습니다.")
            }
            .addOnFailureListener {
                Log.e(LOG, it.message.toString())
                requireContext().showToastMessage("수정에 실패했습니다.")
            }
    }

    private fun nicknameValidation(nickname: String): Boolean {
        return when (Validation.validateNickname(nickname)) {
            null -> {
                binding.etNickname.requestFocus()
                handleInputError(binding.textInputLayoutNickname, "닉네임을 입력해주세요.")
                return false
            }
            false -> {
                binding.etNickname.requestFocus()
                handleInputError(binding.textInputLayoutNickname, "올바르지 않은 닉네임입니다.")
                false
            }
            true -> {
                handleInputSuccess(binding.textInputLayoutNickname)
                return true
            }
        }
    }

    private fun handleInputError(textInputLayout: TextInputLayout, error: String) {
        textInputLayout.error = error
    }

    private fun handleInputSuccess(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }
}