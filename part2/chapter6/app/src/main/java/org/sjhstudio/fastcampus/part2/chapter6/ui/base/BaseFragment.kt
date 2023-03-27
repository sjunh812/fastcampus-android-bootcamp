package org.sjhstudio.fastcampus.part2.chapter6.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter6.ui.LoginActivity
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants

open class BaseFragment<T : ViewBinding>(
    private val bindingInflater: (layoutInflater: LayoutInflater) -> T
) : Fragment() {

    private var _binding: T? = null
    protected val binding: T
        get() = _binding ?: error("ViewBinding error")

    lateinit var database: DatabaseReference
    protected val userId: String? by lazy { Firebase.auth.currentUser?.uid }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(layoutInflater)
        database = Firebase.database(Constants.DB_URL).reference
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (userId == null) {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}