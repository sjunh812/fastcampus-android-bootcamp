package org.sjhstudio.flow.chapter10.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import org.sjhstudio.flow.chapter10.R
import org.sjhstudio.flow.chapter10.databinding.FragmentHomeBinding
import org.sjhstudio.flow.chapter10.model.Article
import org.sjhstudio.flow.chapter10.ui.ArticleAdapter
import org.sjhstudio.flow.chapter10.ui.MainActivity
import org.sjhstudio.flow.chapter10.util.Constants.FIRESTORE_ARTICLE
import org.sjhstudio.flow.chapter10.util.KeepStateNavigator
import org.sjhstudio.flow.chapter10.util.showSnackBar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding
        get() = _binding ?: error("ViewBinding Error")

    private val articleAdapter by lazy {
        ArticleAdapter { article ->
            navigateToArticleFragment(article)
        }
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
        Firebase.firestore.collection(FIRESTORE_ARTICLE)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val list = querySnapshot.map {  documentSnapshot ->
                    documentSnapshot.toObject<Article>()
                }
                articleAdapter.submitList(list)
            }.addOnFailureListener { e ->
                e.printStackTrace()
                binding.root.showSnackBar("게시물을 가져오는 중 오류가 발생했습니다.")
            }
    }

    private fun navigateToArticleFragment(article: Article) {
        article.id?.let { articleId ->
            val direction = HomeFragmentDirections.actionFragmentHomeToFragmentArticle(articleId)
            findNavController().navigate(direction)
        }
    }
}