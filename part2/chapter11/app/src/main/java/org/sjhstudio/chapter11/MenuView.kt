package org.sjhstudio.chapter11

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import org.sjhstudio.chapter11.databinding.ItemMenuBinding

class MenuView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private lateinit var binding: ItemMenuBinding
    private var imageUrl: String? = null
    private var menuName: String? = null

    init {
        attributeSet?.let { set ->
            initAttrs(set)
        }
        initViews()
    }

    private fun initAttrs(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MenuView,
            0,
            0
        ).apply {
            imageUrl = getString(R.styleable.MenuView_imageUrl)
            menuName = getString(R.styleable.MenuView_menuName)
        }
    }

    private fun initViews() {
        val view = inflate(context, R.layout.item_menu, this)
        binding = ItemMenuBinding.bind(view)

        imageUrl?.let { url ->
            setImageUrl(url)
        }
        menuName?.let { name ->
            setMenuName(name)
        }
    }

    fun setImageUrl(url: String) {
        imageUrl = url
        Glide.with(binding.ivMenu)
            .load(url)
            .circleCrop()
            .into(binding.ivMenu)
    }

    fun setMenuName(name: String) {
        menuName = name
        binding.tvMenuName.text = name
    }
}