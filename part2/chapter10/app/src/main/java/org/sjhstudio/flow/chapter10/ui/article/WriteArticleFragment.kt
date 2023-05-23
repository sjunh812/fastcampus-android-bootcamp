package org.sjhstudio.flow.chapter10.ui.article

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.sjhstudio.flow.chapter10.databinding.FragmentWriteArticleBinding
import org.sjhstudio.flow.chapter10.model.Article
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_ARTICLE
import org.sjhstudio.flow.chapter10.util.Constants.STORAGE_ARTICLE
import org.sjhstudio.flow.chapter10.util.Constants.STORAGE_PHOTO
import org.sjhstudio.flow.chapter10.util.showSnackBar
import java.util.*

class WriteArticleFragment : Fragment() {

    private var _binding: FragmentWriteArticleBinding? = null
    val binding: FragmentWriteArticleBinding
        get() = _binding ?: error("ViewBinding Error")

    private var selectedPhotoUri: Uri? = null

    private val pickPhotoResult =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.e(LOG, "Selected URI: $uri")
                selectedPhotoUri = uri
                addPhoto(uri)
            } else {
                Log.e(LOG, "No media selected")
            }
        }

    companion object {
        private const val LOG = "WriteArticleFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(LOG, "onAttach()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWriteArticleBinding.inflate(layoutInflater)
        Log.e(LOG, "onCreateView()")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnUpload.setOnClickListener {
                if (selectedPhotoUri != null) {
                    showProgress()
                    uploadStorage(
                        photoUri = selectedPhotoUri!!,
                        onSuccess = { url ->
                            uploadFirestore(url, etPhotoDescription.text.toString())
                        },
                        onError = { e ->
                            e?.printStackTrace()
                            hideProgress()
                            root.showSnackBar("사진 업로드에 실패했습니다.")
                        }
                    )
                } else {
                    root.showSnackBar("사진을 추가해 주세요.")
                }
            }

            ivPhoto.setOnClickListener {
                pickPhoto()
            }

            btnDeletePhoto.setOnClickListener {
                addPhoto(null)
            }
        }
    }

    private fun pickPhoto() {
        pickPhotoResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun addPhoto(uri: Uri?) {
        binding.ivPhoto.setImageURI(uri)
        binding.tvAddPhoto.isVisible = uri == null
        binding.btnDeletePhoto.isVisible = uri != null
    }

    private fun uploadStorage(
        photoUri: Uri,
        onSuccess: (String) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
        val fileName = "${UUID.randomUUID()}.png"
        val storageReference = Firebase.storage.reference.child(STORAGE_ARTICLE)
            .child(STORAGE_PHOTO)
            .child(fileName)

        storageReference.putFile(photoUri)
            .addOnCompleteListener { uploadTask ->
                if (uploadTask.isSuccessful) {
                    storageReference.downloadUrl
                        .addOnCompleteListener { downloadUrlTask ->
                            if (downloadUrlTask.isSuccessful) {
                                onSuccess.invoke(downloadUrlTask.result.toString())
                            } else {
                                onError(downloadUrlTask.exception)
                            }
                        }
                } else {
                    onError(uploadTask.exception)
                }
            }
    }

    private fun uploadFirestore(photoUrl: String, description: String) {
        val articleId = UUID.randomUUID().toString()
        val article = Article(
            id = articleId,
            createAt = System.currentTimeMillis(),
            imageUrl = photoUrl,
            description = description
        )

        Firebase.firestore.collection(FIRESTORE_ARTICLE).document(articleId)
            .set(article)
            .addOnCompleteListener { task ->
                hideProgress()

                if (task.isSuccessful) {
                    findNavController().popBackStack()
                } else {
                    binding.root.showSnackBar("작성 중 오류가 발생했습니다.")
                }
            }
    }

    private fun showProgress() {
        binding.layoutProgress.isVisible = true
    }

    private fun hideProgress() {
        binding.layoutProgress.isVisible = false
    }
}