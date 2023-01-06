package com.example.githubtest.ui.common.recycler

import android.util.SparseArray
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

class DelegationAdapter<D>(
    private val lifecycleOwner: LifecycleOwner?,
    private val typeToAdapterMap: SparseArray<AdapterDelegate<out D, out ViewBinding>>,
    diffCallbackMap: Map<Class<*>, DiffUtil.ItemCallback<Any>>
) : ListAdapter<D, RecyclerView.ViewHolder>(GenericDiffCallback(diffCallbackMap)), LifecycleObserver {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return typeToAdapterMap[viewType].onCreateViewHolder(parent, viewType, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        typeToAdapterMap[holder.itemViewType].bindInternal(position, currentList, holder, emptyList())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        typeToAdapterMap[holder.itemViewType].bindInternal(position, currentList, holder, payloads)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        typeToAdapterMap[holder.itemViewType].onRecycledInternal(holder)
    }

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        for (i in FIRST_VIEW_TYPE until typeToAdapterMap.size()) {
            val delegate = typeToAdapterMap.valueAt(i)
            if (delegate.isForViewType(item)) {
                return typeToAdapterMap.keyAt(i)
            }
        }

        throw NullPointerException(
            "Can not get viewType for position $position"
        )
    }

    class Builder<D> {

        private var count = FIRST_VIEW_TYPE
        private val typeToAdapterMap: SparseArray<AdapterDelegate<out D, out ViewBinding>> = SparseArray()
        private val diffCallbackMap: MutableMap<Class<*>, DiffUtil.ItemCallback<Any>> = mutableMapOf()

        @Suppress("UNCHECKED_CAST")
        fun <VB : ViewBinding> add(delegate: AdapterDelegate<out D, out VB>, diffCallback: DiffUtil.ItemCallback<out D>?): Builder<D> {
            typeToAdapterMap.put(count++, delegate)
            diffCallback?.also { diffCallbackMap[getDataClass(diffCallback.javaClass)] = diffCallback as DiffUtil.ItemCallback<Any> }
            return this
        }

        fun build(lifecycleOwner: LifecycleOwner?): DelegationAdapter<D> {
            return DelegationAdapter(lifecycleOwner, typeToAdapterMap, diffCallbackMap)
        }

        @Suppress("UNCHECKED_CAST")
        private fun getDataClass(clazz: Class<DiffUtil.ItemCallback<out D>>): Class<D> {
            return ((clazz.genericSuperclass as ParameterizedType)
                .actualTypeArguments[0] as Class<D>)
        }
    }

    companion object {
        private const val FIRST_VIEW_TYPE = 0
    }
}
