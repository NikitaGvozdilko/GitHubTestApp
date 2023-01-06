package com.example.githubtest.utils.extensions

import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
operator fun <T, R> Class<T>.get(index: Int): Class<R> =
    (genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<R>