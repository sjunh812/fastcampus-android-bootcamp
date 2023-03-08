package org.sjhstudio.fastcampus.part2.chapter1.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import org.sjhstudio.fastcampus.part2.chapter1.databinding.FragmentWebtoonBinding
import org.sjhstudio.fastcampus.part2.chapter1.ui.MainActivity.Companion.SHARED_PREFERENCES
import org.sjhstudio.fastcampus.part2.chapter1.webview.WebtoonWebViewClient

class WebtoonFragment(private val position: Int, private val url: String) : Fragment() {

    private lateinit var binding: FragmentWebtoonBinding
    private val webtoonWebViewClient: WebtoonWebViewClient by lazy {
        WebtoonWebViewClient(binding.progressBar) { url -> setLastWebtoonHistory(url) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebtoonBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()
    }

    override fun onResume() {
        super.onResume()
        initBackPressed()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        with(binding) {
            webView.apply {
                webViewClient = webtoonWebViewClient
                settings.javaScriptEnabled = true
                loadUrl(this@WebtoonFragment.url)
            }

            btnBackToLast.setOnClickListener {
                val history = getLastWebtoonHistoryOrNull()

                if (history == null) {
                    Toast.makeText(requireContext(), "마지막 저장 시점이 없습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    webView.loadUrl(history)
                }
            }

            btnChangeTabName.setOnClickListener {
                val editText = EditText(requireContext())

                AlertDialog.Builder(requireContext()).apply {
                    setTitle("변경할 탭 이름을 입력하세요.")
                    setView(editText)
                    setPositiveButton("확인") { _, _ -> setTabNameToMainActivity(editText.text.toString()) }
                    setNegativeButton("취소", null)
                }.show()
            }
        }
    }

    private fun initBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("handleOnBackPressed()", position.toString())

                    with(binding.webView) {
                        if (canGoBack()) goBack() else requireActivity().finish()
                    }
                }
            })
    }

    private fun setLastWebtoonHistory(url: String) {
        requireContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .edit { putString("tab$position", url) }
    }

    private fun getLastWebtoonHistoryOrNull(): String? {
        return requireContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .getString("tab$position", "").takeIf { !it.isNullOrEmpty() }
    }

    private fun setTabNameToMainActivity(name: String) {
        requireContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .edit { putString("tab${position}_name", name) }
        (activity as? MainActivity)?.setTabName(position, name)
    }
}