package com.sjhstudio.compose.movieapp.features.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.navigation.fragment.navArgs
import com.sjhstudio.compose.movieapp.BaseDialogFragment
import com.sjhstudio.compose.movieapp.R
import com.sjhstudio.compose.movieapp.ui.components.dialogs.Default
import com.sjhstudio.compose.movieapp.ui.components.dialogs.DialogPopup
import com.sjhstudio.compose.movieapp.ui.models.DialogButton
import com.sjhstudio.compose.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IMDBDialogFragment : BaseDialogFragment() {

    private val args: IMDBDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        dialog?.apply {
            isCancelable = true
            setCanceledOnTouchOutside(true)
        }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(strategy = ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MovieAppTheme {
                    DialogPopup.Default(
                        title = stringResource(id = R.string.title_dialog_popup_imdb),
                        bodyText = stringResource(id = R.string.body_dialog_popup_imdb),
                        buttons = listOf(
                            DialogButton.Primary(title = stringResource(id = R.string.btn_open)) {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("$BASE_URL_IMDB${args.url}")
                                    )
                                )
                            },
                            DialogButton.SecondaryBorderless(title = stringResource(id = R.string.btn_cancel)) {
                                dismiss()
                            }
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val BASE_URL_IMDB = "https://m.imdb.com"
    }
}