package com.example.githubtest.utils.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.scrollEndFlow(loadNextPage: () -> Unit) {
    val linearLayoutManager: LinearLayoutManager = layoutManager as LinearLayoutManager
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        private var lastPosition: Int = 0
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val itemsCount = linearLayoutManager.childCount
            if (itemsCount > 0 && linearLayoutManager.findLastCompletelyVisibleItemPosition() > itemsCount - 4
                && linearLayoutManager.findLastCompletelyVisibleItemPosition() != lastPosition
            ) {
                lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                loadNextPage()
            }
        }
    })
}