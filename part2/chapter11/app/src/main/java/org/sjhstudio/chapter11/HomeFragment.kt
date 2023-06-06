package org.sjhstudio.chapter11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.sjhstudio.chapter11.databinding.FragmentHomeBinding
import org.sjhstudio.chapter11.util.readData

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding
        get() = _binding ?: error("ViewBinding Error")

    private val mockData by lazy {
        context?.readData() ?: error("IOException Error")
    }

    companion object {
        private const val LOG = "HomeFragment"
    }

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
            Glide.with(ivAppbar)
                .load(mockData.appbarImageUrl)
                .into(ivAppbar)

            tvAppbarTitle.text = getString(R.string.title_appbar, mockData.user.nickname)
            tvCurrentStarCount.text = mockData.user.currentStarCount
            tvTotalStarCount.text = mockData.user.totalStarCount

            progressBarStar.apply {
                progress = mockData.user.currentStarCount.toIntOrNull() ?: 0
                max = mockData.user.totalStarCount.toIntOrNull() ?: 0
            }

            recommendMenuList.layoutMenu.addView(
                MenuView(requireContext()).apply {
                    setImageUrl("https://picsum.photos/200/200")
                    setMenuName("아이스 카페 아메리카노")
                }
            )
        }
    }
}