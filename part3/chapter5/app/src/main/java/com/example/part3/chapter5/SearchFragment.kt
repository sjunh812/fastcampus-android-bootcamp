package com.example.part3.chapter5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.part3.chapter5.databinding.FragmentSearchBinding
import com.example.part3.chapter5.list.ListAdapter

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_search

    private val adapter by lazy { ListAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun FragmentSearchBinding.initViews() {
        rv.adapter = adapter
    }

    fun searchKeyword(keyword: String) {

    }
}