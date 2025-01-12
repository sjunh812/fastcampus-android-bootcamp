package com.sjhstudio.compose.movieapp.features.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.sjhstudio.compose.movieapp.features.detail.presentation.screen.DetailScreen
import com.sjhstudio.compose.movieapp.features.detail.presentation.viewmodel.MovieDetailViewModel
import com.sjhstudio.compose.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MovieAppTheme {
                    DetailScreen(
                        state = viewModel.detailState.collectAsState(),
                        inputs = viewModel.inputs
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        lifecycleScope.launch {
            viewModel.initMovieName(args.movieName)
        }
    }
}