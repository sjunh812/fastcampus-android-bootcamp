package org.sjhstudio.chapter11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.sjhstudio.chapter11.databinding.FragmentHomeBinding
import org.sjhstudio.chapter11.model.Home
import org.sjhstudio.chapter11.model.Menu
import org.sjhstudio.chapter11.util.readData

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding
        get() = _binding ?: error("ViewBinding Error")

    private val mockHomeData by lazy {
        context?.readData("home.json", Home::class.java) ?: error("IOException Error")
    }
    private val mockMenuData by lazy {
        context?.readData("menu.json", Menu::class.java) ?: error("IOException Error")
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
            initAppbar()
            initRecommendMenuList()
            initBanner()
            initFoodList()
        }
    }

    private fun FragmentHomeBinding.initAppbar() {
        Glide.with(ivAppbar)
            .load(mockHomeData.appbarImageUrl)
            .into(ivAppbar)

        tvAppbarTitle.text = getString(R.string.title_appbar, mockHomeData.user.nickname)
        tvCurrentStarCount.text = mockHomeData.user.currentStarCount
        tvTotalStarCount.text = mockHomeData.user.totalStarCount

        progressBarStar.apply {
            progress = mockHomeData.user.currentStarCount.toIntOrNull() ?: 0
            max = mockHomeData.user.totalStarCount.toIntOrNull() ?: 0
        }
    }

    private fun FragmentHomeBinding.initRecommendMenuList() {
        recommendMenuList.tvTitle.text =
            getString(R.string.title_recommend_menu, mockHomeData.user.nickname)
        mockMenuData.drinks.forEach { drink ->
            recommendMenuList.layoutMenu.addView(
                MenuView(requireContext()).apply {
                    setMenuName(drink.name)
                    setImageUrl(drink.imageUrl)
                }
            )
        }
    }

    private fun FragmentHomeBinding.initBanner() {
        bannerHome.ivBannerHome.apply {
            Glide.with(this)
                .load(mockHomeData.banner.imageUrl)
                .into(this)
            contentDescription = mockHomeData.banner.contentDescription
        }
    }

    private fun FragmentHomeBinding.initFoodList() {
        foodList.tvTitle.text = getString(R.string.title_food_menu)
        mockMenuData.foods.forEach { food ->
            foodList.layoutMenu.addView(
                MenuView(requireContext()).apply {
                    setMenuName(food.name)
                    setImageUrl(food.imageUrl)
                }
            )
        }
    }
}