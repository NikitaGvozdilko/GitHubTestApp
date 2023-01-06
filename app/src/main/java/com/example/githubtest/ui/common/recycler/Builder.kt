package com.example.githubtest.ui.common.recycler

import androidx.lifecycle.LifecycleOwner

fun <D> BuildDelegationAdapter(lifecycleOwner: LifecycleOwner? = null, block: DelegationAdapter.Builder<D>.() -> Unit): DelegationAdapter<D> {
    val builder = DelegationAdapter.Builder<D>()
    block(builder)
    return builder.build(lifecycleOwner)
}
