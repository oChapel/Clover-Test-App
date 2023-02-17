package com.cloverr.clovertestapp.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersHolder {
    fun getMain(): CoroutineDispatcher
    fun getIO(): CoroutineDispatcher
}