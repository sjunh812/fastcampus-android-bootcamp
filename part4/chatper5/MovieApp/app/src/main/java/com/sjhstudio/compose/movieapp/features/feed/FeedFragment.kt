package com.sjhstudio.compose.movieapp.features.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sjhstudio.compose.movieapp.features.feed.presentation.output.FeedUiEffect
import com.sjhstudio.compose.movieapp.features.feed.presentation.screen.FeedScreen
import com.sjhstudio.compose.movieapp.features.feed.presentation.viewmodel.FeedViewModel
import com.sjhstudio.compose.movieapp.ui.navigation.safeNavigation
import com.sjhstudio.compose.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MovieAppTheme {
                    FeedScreen(
                        feedStateHolder = viewModel.output.feedState.collectAsState(),
                        input = viewModel.input,
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiEffects()
    }

    private fun observeUiEffects() {
        val navController = findNavController()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.output.feedUiEffect.collectLatest {
                    when (it) {
                        FeedUiEffect.OpenInfoDialog -> {
                            // TODO.
                        }

                        is FeedUiEffect.OpenMovieDetail -> {
                            navController.safeNavigation(FeedFragmentDirections.actionFeedToDetail(it.movieName))
                        }
                    }
                }
            }
        }
    }
}