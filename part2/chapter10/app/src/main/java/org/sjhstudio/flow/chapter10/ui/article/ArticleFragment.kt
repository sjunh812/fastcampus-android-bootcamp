package org.sjhstudio.flow.chapter10.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import org.sjhstudio.flow.chapter10.databinding.FragmentArticleBinding
import org.sjhstudio.flow.chapter10.model.Article
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_ARTICLE
import org.sjhstudio.flow.chapter10.util.showSnackBar

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    val binding: FragmentArticleBinding
        get() = _binding ?: error("ViewBinding Error")

    private val navArgs: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        callArticle()
    }

    private fun initViews() {
        with(binding) {
            toolbar.setupWithNavController(findNavController())
        }
    }

    private fun callArticle() {
        Firebase.firestore.collection(FIRESTORE_ARTICLE).document(navArgs.articleId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                updateArticleUI(documentSnapshot.toObject<Article>() ?: return@addOnSuccessListener)
            }.addOnFailureListener { e ->
                e.printStackTrace()
                binding.root.showSnackBar("게시물을 가져오는 중 오류가 발생했습니다.")
            }
    }

    private fun updateArticleUI(article: Article) {
        with(binding) {
            Glide.with(ivPhoto)
                .load(article.imageUrl)
                .into(ivPhoto)

            tvDescription.text = article.description
        }
    }
}