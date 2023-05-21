package org.sjhstudio.flow.chapter10.ui.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import org.sjhstudio.flow.chapter10.databinding.FragmentWriteArticleBinding

class WriteArticleFragment : Fragment() {

    private var _binding: FragmentWriteArticleBinding? = null
    val binding: FragmentWriteArticleBinding
        get() = _binding ?: error("ViewBinding Error")

    private val pickPhotoResult = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.e(LOG, "Selected URI: $uri")
        } else {
            Log.e(LOG, "No media selected")
        }
    }

    companion object {
        private const val LOG = "WriteArticleFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWriteArticleBinding.inflate(layoutInflater)

        pickPhotoResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}