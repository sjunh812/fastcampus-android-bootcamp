package org.sjhstudio.fastcampus.part2.chapter6.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.sjhstudio.fastcampus.part2.chapter6.R
import org.sjhstudio.fastcampus.part2.chapter6.databinding.FragmentUserBinding
import org.sjhstudio.fastcampus.part2.chapter6.ui.user.adapter.UserAdapter

class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding
    private val userAdapter: UserAdapter by lazy { UserAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            rvUser.apply {
                adapter = userAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}