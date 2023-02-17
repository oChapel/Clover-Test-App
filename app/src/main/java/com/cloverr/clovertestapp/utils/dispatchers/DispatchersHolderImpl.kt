package com.cloverr.clovertestapp.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatchersHolderImpl @Inject constructor() : DispatchersHolder {

    override fun getMain(): CoroutineDispatcher = Dispatchers.Main

    override fun getIO(): CoroutineDispatcher = Dispatchers.IO
}