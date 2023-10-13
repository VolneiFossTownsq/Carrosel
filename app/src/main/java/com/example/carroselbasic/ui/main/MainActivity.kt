package com.example.carroselbasic.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.carroselbasic.ui.main.adapter.CarouselAdapter
import com.example.carroselbasic.R
import com.example.carroselbasic.data.Carousel
import com.example.carroselbasic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val carouselAdapter = CarouselAdapter()
    private var currentPosition: Int = RecyclerView.NO_POSITION
    private val snapHelper = LinearSnapHelper()
    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private lateinit var autoScrollRunnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupViews()
    }

    private fun setupViews() {
        carouselAdapter.setItems(
            arrayListOf(
                Carousel(
                    1,
                    "https://t2.tudocdn.net/350886?w=1920"
                ),
                Carousel(
                    2,
                    "https://cooljsonline.com/wp-content/uploads/2020/05/nike-web-banner-main-1.jpg"
                ),
                Carousel(
                    3,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYlPBJXagL_UORyEJavpzL7You3VNp24BHrA&usqp=CAU"
                )
            )
        )

        binding?.let { b ->
            val recyclerView = b.rvCarousel
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = carouselAdapter

            snapHelper.attachToRecyclerView(recyclerView)

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        notifyPositionChanged(recyclerView)
                    }
                }
            })

            addDots(b.dots, carouselAdapter.itemCount, 0)
        }

        autoScrollRunnable = Runnable {
            val layoutManager = binding?.rvCarousel?.layoutManager as? LinearLayoutManager
            val currentPosition = layoutManager?.findFirstVisibleItemPosition() ?: 0
            val nextPosition = if (currentPosition < carouselAdapter.itemCount - 1) currentPosition + 1 else 0
            binding?.rvCarousel?.smoothScrollToPosition(nextPosition)


            val delayMillis = 3000
            autoScrollHandler.postDelayed(autoScrollRunnable, delayMillis.toLong())
        }


        autoScrollHandler.postDelayed(autoScrollRunnable, 3000)
    }

    private fun addDots(container: LinearLayout, size: Int, position: Int) {
        container.removeAllViews()

        for (i in 0 until size) {
            val textView = TextView(this).apply {
                text = getString(R.string.dotted)
                textSize = 38f
                setTextColor(
                    if (position == i) ContextCompat.getColor(context, android.R.color.black)
                    else ContextCompat.getColor(context, android.R.color.darker_gray)
                )
            }
            container.addView(textView)
        }
    }

    private fun notifyPositionChanged(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        val view = snapHelper.findSnapView(layoutManager)
        val position = if (view == null) RecyclerView.NO_POSITION else layoutManager?.getPosition(view)

        val positionChanged = currentPosition != position
        if (positionChanged) {
            addDots(binding!!.dots, carouselAdapter.itemCount, position ?: 0)
        }
        currentPosition = position ?: RecyclerView.NO_POSITION
    }

}

