package ru.vladislavmitin.bsc.app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ru.vladislavmitin.bsc.di.DaggerAppComponent

class App: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}