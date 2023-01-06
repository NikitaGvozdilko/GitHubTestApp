package com.example.githubtest.ui.common.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

interface AdapterDelegate<Data, Binding : ViewBinding> {

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int, lifecycleOwner: LifecycleOwner?): RecyclerView.ViewHolder
    fun isForViewType(item: Any?): Boolean

    fun bindInternal(position: Int, items: List<*>, holder: RecyclerView.ViewHolder, payloads: List<Any>)
    fun onRecycledInternal(holder: RecyclerView.ViewHolder)

    fun onCreated(holder: AdapterViewHolder<Data, Binding>)

    fun onResume(holder: RecyclerView.ViewHolder)

    fun onPause(holder: RecyclerView.ViewHolder)
}

@Suppress("UNCHECKED_CAST")
abstract class BaseAdapterDelegate<Data, Binding : ViewBinding> : AdapterDelegate<Data, Binding> {

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, lifecycleOwner: LifecycleOwner?): RecyclerView.ViewHolder {
        val vbClass = ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<Binding>)
        val method = vbClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        val binging = method.invoke(null, LayoutInflater.from(parent.context), parent, false) as Binding
        return AdapterViewHolder<Data, Binding>(binging, parent.context, lifecycleOwner).apply {
            onCreated(this)
        }
    }

    final override fun bindInternal(position: Int, items: List<*>, holder: RecyclerView.ViewHolder, payloads: List<Any>) {
        holder as AdapterViewHolder<Data, Binding>
        holder.onBind(items[position] as Data, payloads)
    }

    final override fun onRecycledInternal(holder: RecyclerView.ViewHolder) {
        holder as AdapterViewHolder<Data, Binding>
        holder.onRecycled()
    }

    override fun onCreated(holder: AdapterViewHolder<Data, Binding>) {}

    final override fun onResume(holder: RecyclerView.ViewHolder) {
        holder as AdapterViewHolder<Data, Binding>
        holder.onResume()
    }

    final override fun onPause(holder: RecyclerView.ViewHolder) {
        holder as AdapterViewHolder<Data, Binding>
        holder.onPause()
    }
}
