package com.sjhstudio.compose.movieapp.features.dialog

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
import com.sjhstudio.compose.movieapp.ui.components.dialogs.DialogPopup
import com.sjhstudio.compose.movieapp.ui.components.dialogs.Rating
import com.sjhstudio.compose.movieapp.ui.models.DialogButton
import com.sjhstudio.compose.movieapp.ui.models.buttons.LeadingIconData
import com.sjhstudio.compose.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingDialogFragment : BaseDialogFragment() {

    private val args: RatingDialogFragmentArgs by navArgs()

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
                    DialogPopup.Rating(
                        title = stringResource(id = R.string.title_dialog_popup_rating),
                        bodyText = args.movieName,
                        rating = args.rating,
                        buttons = listOf(
                            DialogButton.Primary(
                                title = stringResource(id = R.string.btn_submit),
                                leadingIconData = LeadingIconData(
                                    iconDrawable = R.drawable.ic_send,
                                    iconContentDescription = R.string.description_btn_send
                                )
                            ) {
                                dismiss()
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
}