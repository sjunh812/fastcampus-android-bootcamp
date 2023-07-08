package org.sjhstudio.chapter11

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.sjhstudio.chapter11.databinding.FragmentOrderBinding
import org.sjhstudio.chapter11.model.Menu
import org.sjhstudio.chapter11.model.MenuItem
import org.sjhstudio.chapter11.util.readData
import kotlin.math.abs

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    val binding: FragmentOrderBinding
        get() = _binding ?: error("ViewBinding Error")

    private val orderMenuAdapter by lazy { OrderMenuAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        with(binding) {
            rvOrder.run {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = orderMenuAdapter
            }
            appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                Log.e("sjh", "totalScrollRange ${appbar.totalScrollRange} verticalOffset $verticalOffset")
                val seekPosition = abs(verticalOffset) / appbar.totalScrollRange.toFloat()
                layoutMotionToolbar.progress = seekPosition
            }
        }
    }

    private fun initData() {
        val mockOrderMenuData = requireContext().readData("menu.json", Menu::class.java)?.drinks ?: return
        orderMenuAdapter.submitList(mockOrderMenuData)
    }
}