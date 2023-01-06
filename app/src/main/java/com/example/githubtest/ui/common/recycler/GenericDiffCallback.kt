package com.example.githubtest.ui.common.recycler

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class GenericDiffCallback<D>(
    private val diffCallbackMap: Map<Class<*>, DiffUtil.ItemCallback<Any>>
) : DiffUtil.ItemCallback<D>() {

    override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
        if ((oldItem as Any).javaClass == (newItem as Any).javaClass) {
            return diffCallbackMap[oldItem.javaClass]?.areItemsTheSame(oldItem, newItem) ?: false
        }

        return false
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
        if ((oldItem as Any).javaClass == (newItem as Any).javaClass) {
            return diffCallbackMap[oldItem.javaClass]?.areContentsTheSame(oldItem, newItem) ?: false
        }

        return false
    }

    override fun getChangePayload(oldItem: D, newItem: D): Any? {
        if ((oldItem as Any).javaClass == (newItem as Any).javaClass) {
            return diffCallbackMap[oldItem.javaClass]?.getChangePayload(oldItem, newItem)
        }

        return null
    }
}
