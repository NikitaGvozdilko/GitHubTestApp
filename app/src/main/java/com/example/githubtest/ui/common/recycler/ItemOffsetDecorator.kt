package com.example.githubtest.ui.common.recycler


import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/***
 * Can be used for multiple view types
 */
class ItemOffsetDecoration(
    params: Map<Class<*>, Params> = mapOf()
) : RecyclerView.ItemDecoration() {

    private val paramsMap: MutableMap<Class<*>, Params> = mutableMapOf()

    init {
        if (params.isNotEmpty()) {
            paramsMap.putAll(params)
        }
    }

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val viewType =
            (parent.getChildViewHolder(view) as AdapterViewHolder<*, *>).item!!::class.java
        paramsMap[viewType]?.apply {
            if (firstItemOffsetsRect != null && parent.getChildAdapterPosition(view) == 0) {
                outRect.left = firstItemOffsetsRect.left
                outRect.right = firstItemOffsetsRect.right
                outRect.top = firstItemOffsetsRect.top
                outRect.bottom = firstItemOffsetsRect.bottom
            } else if (lastItemOffsetsRect != null
                && parent.adapter != null
                && parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1
            ) {
                outRect.left = lastItemOffsetsRect.left
                outRect.right = lastItemOffsetsRect.right
                outRect.top = lastItemOffsetsRect.top
                outRect.bottom = lastItemOffsetsRect.bottom
            } else {
                outRect.right = regularOffsetsRect.right
                outRect.left = regularOffsetsRect.left
                outRect.top = regularOffsetsRect.top
                outRect.bottom = regularOffsetsRect.bottom
            }
        } ?: run {
            outRect.right = 0
            outRect.left = 0
            outRect.top = 0
            outRect.bottom = 0
        }
    }

    fun addParams(viewType: Class<*>, params: Params) {
        paramsMap[viewType] = params
    }

    /**
     * If [firstItemOffsetsRect] or [lastItemOffsetsRect] be null [regularOffsetsRect] params will be used
     */
    class Params(
        val regularOffsetsRect: Rect = Rect(0, 0, 0, 0),
        val firstItemOffsetsRect: Rect? = null,
        val lastItemOffsetsRect: Rect? = null
    )
}
