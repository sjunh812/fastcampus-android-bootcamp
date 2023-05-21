package org.sjhstudio.flow.chapter10.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.sjhstudio.flow.chapter10.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding
        get() = _binding ?: error("ViewBinding Error")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnWriteArticle.setOnClickListener {
                val direction = HomeFragmentDirections.actionHomeFragmentToWriteArticleFragment()
                findNavController().navigate(direction)
            }
        }
    }
}