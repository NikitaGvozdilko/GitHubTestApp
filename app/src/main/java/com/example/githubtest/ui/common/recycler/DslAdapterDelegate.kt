package com.example.githubtest.ui.common.recycler

import androidx.viewbinding.ViewBinding

inline fun <reified D, VB : ViewBinding> adapterDelegate(crossinline block: AdapterViewHolder<D, VB>.() -> Unit): AdapterDelegate<D, VB> {
    return object : BaseAdapterDelegate<D, VB>() {

        override fun onCreated(holder: AdapterViewHolder<D, VB>) {
            block(holder)
        }

        override fun isForViewType(item: Any?): Boolean {
            return item is D
        }
    }
}
