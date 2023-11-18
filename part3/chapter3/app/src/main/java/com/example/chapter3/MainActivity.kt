package com.example.chapter3

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.example.chapter3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.firstCard.setOnCardClickListener(endStateId = R.id.first_card_on_top)
        binding.secondCard.setOnCardClickListener(endStateId = R.id.second_card_on_top)
        binding.thirdCard.setOnCardClickListener(endStateId = R.id.third_card_on_top)
    }

    private fun View.setOnCardClickListener(@IdRes endStateId: Int) = setOnClickListener {
        with(binding) {
            when (root.currentState) {
                R.id.fan_out -> {
                    root.setTransition(R.id.fan_out, R.id.first_card_on_top)
                    root.transitionToEnd()
                    collapsedCardCompletedListener(endStateId)
                }

                R.id.first_card_on_top -> {
                    if (endStateId == R.id.first_card_on_top) {
                        openDetail(firstCard, tvFirstCardTitle.text)
                    } else {
                        root.setTransition(R.id.first_card_on_top, endStateId)
                        root.transitionToEnd()
                    }
                }

                R.id.second_card_on_top -> {
                    if (endStateId == R.id.second_card_on_top) {
                        openDetail(secondCard, tvSecondCardTitle.text)
                    } else {
                        root.setTransition(R.id.second_card_on_top, endStateId)
                        root.transitionToEnd()
                    }
                }

                R.id.third_card_on_top -> {
                    if (endStateId == R.id.third_card_on_top) {
                        openDetail(thirdCard, tvThirdCardTitle.text)
                    } else {
                        root.setTransition(R.id.third_card_on_top, endStateId)
                        root.transitionToEnd()
                    }
                }
            }
        }
    }

    private fun openDetail(view: View, cardName: CharSequence) {
        view.transitionName = "card"
        val optionCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(view, view.transitionName))
        DetailActivity.start(
            context = this,
            cardName = cardName.toString(),
            cardColor = view.backgroundTintList,
            optionCompat = optionCompat
        )
    }

    private fun collapsedCardCompletedListener(@IdRes endStateId: Int) {
        with(binding) {
            root.setTransitionListener(object : TransitionAdapter() {
                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    if (currentId == R.id.first_card_on_top) {
                        root.setTransition(R.id.first_card_on_top, endStateId)
                        root.transitionToEnd()
                    }
                    root.setTransitionListener(null)
                }
            })
        }
    }
}