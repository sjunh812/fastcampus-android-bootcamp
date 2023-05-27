package org.sjhstudio.flow.chapter10.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import org.sjhstudio.flow.chapter10.databinding.FragmentBookmarkBinding
import org.sjhstudio.flow.chapter10.model.Article
import org.sjhstudio.flow.chapter10.model.ArticleDto
import org.sjhstudio.flow.chapter10.ui.adapter.ArticleAdapter
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_ARTICLE
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_ARTICLE_ID
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_BOOKMARK
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_BOOKMARK_ARTICLE_ID
import org.sjhstudio.flow.chapter10.util.showSnackBar

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    val binding: FragmentBookmarkBinding
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        callBookmarks()
    }

    private fun initViews() {
        with(binding) {
            toolbar.setupWithNavController(findNavController())
            rvArticle.adapter = articleAdapter
        }
    }

    private fun callBookmarks() {
        val uid = Firebase.auth.currentUser?.uid ?: return

        Firebase.firestore.collection(FIRESTORE_BOOKMARK)
            .document(uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val bookmarkIds = documentSnapshot.get(FIRESTORE_BOOKMARK_ARTICLE_ID) as List<*>?

                if (bookmarkIds.isNullOrEmpty()) {
                    binding.root.showSnackBar("북마크가 없습니다.")
                    return@addOnSuccessListener
                }

                Firebase.firestore.collection(FIRESTORE_ARTICLE)
                    .whereIn(FIRESTORE_ARTICLE_ID, bookmarkIds)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val list = querySnapshot.map { article ->
                            article.toObject<ArticleDto>()
                        }.map { articleDto ->
                            articleDto.toArticle(isBookmark = true)
                        }

                        articleAdapter.submitList(list)
                    }.addOnFailureListener { e ->
                        e.printStackTrace()
                    }
            }.addOnFailureListener { e ->
                e.printStackTrace()
                binding.root.showSnackBar("북마크를 불러오는 중 오류가 발생했습니다.")
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
            ).addOnSuccessListener {
                callBookmarks()
            }.addOnFailureListener { e ->
                if (e is FirebaseFirestoreException && e.code == FirebaseFirestoreException.Code.NOT_FOUND) {
                    Firebase.firestore.collection(FIRESTORE_BOOKMARK).document(uid)
                        .set(hashMapOf(FIRESTORE_BOOKMARK_ARTICLE_ID to listOf(articleId)))
                        .addOnSuccessListener {
                            callBookmarks()
                        }
                }
            }
    }

    private fun navigateToArticleFragment(article: Article) {
        val direction =
            BookmarkFragmentDirections.actionBookmarkFragmentToFragmentArticle(
                article.id,
                article.isBookmark
            )
        findNavController().navigate(direction)
    }
}