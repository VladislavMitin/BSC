package ru.vladislavmitin.bsc.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.vladislavmitin.bsc.ui.DetailFragment
import ru.vladislavmitin.bsc.ui.ListFragment

@Module
abstract class UiComponentModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [UiModule::class])
    abstract fun contributeEventListAndroidInjector(): ListFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [UiModule::class])
    abstract fun contributeEventDetailAndroidInjector(): DetailFragment
}