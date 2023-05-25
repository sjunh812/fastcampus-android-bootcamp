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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import org.sjhstudio.flow.chapter10.R
import org.sjhstudio.flow.chapter10.databinding.FragmentArticleBinding
import org.sjhstudio.flow.chapter10.model.Article
import org.sjhstudio.flow.chapter10.model.ArticleDto
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_ARTICLE
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_BOOKMARK
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_BOOKMARK_ARTICLE_ID
import org.sjhstudio.flow.chapter10.util.showSnackBar

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    val binding: FragmentArticleBinding
        get() = _binding ?: error("ViewBinding Error")

    private val navArgs: ArticleFragmentArgs by navArgs()
    private var isBookmark = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(layoutInflater)
        isBookmark = navArgs.isBookmark

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
            btnBookmark.apply {
                setImageResource(if (isBookmark) R.drawable.ic_bookmark_24 else R.drawable.ic_bookmark_border_24)
                setOnClickListener {
                    isBookmark = isBookmark.not()
                    updateBookmark()
                    setImageResource(if (isBookmark) R.drawable.ic_bookmark_24 else R.drawable.ic_bookmark_border_24)
                }
            }
        }
    }

    private fun callArticle() {
        Firebase.firestore.collection(FIRESTORE_ARTICLE).document(navArgs.articleId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val articleDto =
                    documentSnapshot.toObject<ArticleDto>() ?: return@addOnSuccessListener
                val article = Article(
                    id = articleDto.id.orEmpty(),
                    imageUrl = articleDto.imageUrl.orEmpty(),
                    description = articleDto.description.orEmpty(),
                    isBookmark = navArgs.isBookmark
                )

                updateArticleUI(article)
            }.addOnFailureListener { e ->
                e.printStackTrace()
                binding.root.showSnackBar("게시물을 가져오는 중 오류가 발생했습니다.")
            }
    }

    private fun updateBookmark() {
        val uid = Firebase.auth.currentUser?.uid ?: return

        Firebase.firestore.collection(FIRESTORE_BOOKMARK).document(uid)
            .update(
                FIRESTORE_BOOKMARK_ARTICLE_ID,
                if (isBookmark) {
                    FieldValue.arrayUnion(navArgs.articleId)
                } else {
                    FieldValue.arrayRemove(navArgs.articleId)
                }
            ).addOnSuccessListener {
                binding.root.showSnackBar(if (isBookmark) "북마크에 추가되었습니다." else "북마크가 삭제되었습니다.")
            }.addOnFailureListener { e ->
                if (e is FirebaseFirestoreException && e.code == FirebaseFirestoreException.Code.NOT_FOUND) {
                    Firebase.firestore.collection(FIRESTORE_BOOKMARK).document(uid)
                        .set(hashMapOf(FIRESTORE_BOOKMARK_ARTICLE_ID to listOf(navArgs.articleId)))
                        .addOnSuccessListener {
                            binding.root.showSnackBar("북마크에 추가되었습니다.")
                        }
                }
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