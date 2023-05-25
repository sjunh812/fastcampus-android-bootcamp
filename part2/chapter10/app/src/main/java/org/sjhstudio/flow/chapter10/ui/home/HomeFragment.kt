package org.sjhstudio.flow.chapter10.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import org.sjhstudio.flow.chapter10.databinding.FragmentHomeBinding
import org.sjhstudio.flow.chapter10.model.Article
import org.sjhstudio.flow.chapter10.model.ArticleDto
import org.sjhstudio.flow.chapter10.ui.adapter.ArticleAdapter
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_ARTICLE
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_BOOKMARK
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_BOOKMARK_ARTICLE_ID
import org.sjhstudio.flow.chapter10.util.showSnackBar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding
        get() = _binding ?: error("ViewBinding Error")

    private val articleAdapter by lazy {
        ArticleAdapter(
            onItemClicked = { article ->
                navigateToArticleFragment(article)
            },
            onBookmarkClicked = { articleId, isBookmark ->
                updateBookmark(articleId, isBookmark)
            }
        )
    }

    companion object {
        private const val LOG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        callArticles()
    }

    private fun initViews() {
        with(binding) {
            rvArticle.adapter = articleAdapter

            btnBookmark.setOnClickListener {
                if (Firebase.auth.currentUser == null) {
                    return@setOnClickListener
                }

                val direction = HomeFragmentDirections.actionFragmentHomeToBookmarkFragment()
                findNavController().navigate(direction)
            }

            btnWriteArticle.setOnClickListener {
                if (Firebase.auth.currentUser == null) {
                    return@setOnClickListener
                }

                val direction = HomeFragmentDirections.actionHomeFragmentToWriteArticleFragment()
                findNavController().navigate(direction)
            }
        }
    }

    private fun callArticles() {
        val uid = Firebase.auth.currentUser?.uid ?: return

        Firebase.firestore.collection(FIRESTORE_BOOKMARK)
            .document(uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val bookmarkIds = documentSnapshot.get(FIRESTORE_BOOKMARK_ARTICLE_ID) as List<*>?

                Firebase.firestore.collection(FIRESTORE_ARTICLE)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val list = querySnapshot.map { documentSnapshot ->
                            documentSnapshot.toObject<ArticleDto>()
                        }.map { articleDto ->
                            Article(
                                id = articleDto.id.orEmpty(),
                                imageUrl = articleDto.imageUrl.orEmpty(),
                                description = articleDto.description.orEmpty(),
                                isBookmark = bookmarkIds?.contains(articleDto.id.orEmpty()) ?: false
                            )
                        }

                        articleAdapter.submitList(list)
                    }.addOnFailureListener { e ->
                        e.printStackTrace()
                        binding.root.showSnackBar("게시물을 가져오는 중 오류가 발생했습니다.")
                    }
            }.addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun updateBookmark(articleId: String, isBookmark: Boolean) {
        val uid = Firebase.auth.currentUser?.uid ?: return

        Firebase.firestore.collection(FIRESTORE_BOOKMARK).document(uid)
            .update(
                FIRESTORE_BOOKMARK_ARTICLE_ID,
                if (isBookmark) {
                    FieldValue.arrayUnion(articleId)
                } else {
                    FieldValue.arrayRemove(articleId)
                }
            ).addOnFailureListener { e ->
                if (e is FirebaseFirestoreException && e.code == FirebaseFirestoreException.Code.NOT_FOUND) {
                    Firebase.firestore.collection(FIRESTORE_BOOKMARK).document(uid)
                        .set(hashMapOf(FIRESTORE_BOOKMARK_ARTICLE_ID to listOf(articleId)))
                }
            }
    }

    private fun navigateToArticleFragment(article: Article) {
        Log.e(LOG, "${article.isBookmark}")
        val direction = HomeFragmentDirections.actionFragmentHomeToFragmentArticle(
            articleId = article.id,
            isBookmark = article.isBookmark
        )
        findNavController().navigate(direction)
    }
}