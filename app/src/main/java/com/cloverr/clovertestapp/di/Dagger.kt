package com.cloverr.clovertestapp.di

import android.accounts.Account
import android.content.Context
import com.clover.sdk.util.CloverAccount
import com.cloverr.clovertestapp.App
import com.cloverr.clovertestapp.background.OrderChangedActionReceiver
import com.cloverr.clovertestapp.utils.dispatchers.DispatchersHolder
import com.cloverr.clovertestapp.utils.dispatchers.DispatchersHolderImpl
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(receiver: OrderChangedActionReceiver)
}

@Module(includes = [AbstractModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = App.instance

    @Provides
    @Singleton
    fun provideCloverAccount(appContext: Context): Account = CloverAccount.getAccount(appContext)
}

@Module
abstract class AbstractModule {

    @Binds
    abstract fun provideDispatchersHolder(
        dispatchersHolderImpl: DispatchersHolderImpl
    ): DispatchersHolder
}