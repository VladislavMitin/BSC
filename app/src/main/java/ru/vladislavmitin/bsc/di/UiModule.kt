package ru.vladislavmitin.bsc.di

import dagger.Module
import dagger.Provides
import ru.vladislavmitin.bsc.domain.IEventRepo
import ru.vladislavmitin.bsc.ui.MainViewModelFactory

@Module
class UiModule {
    @ActivityScope
    @Provides
    fun provideViewModelFactory(eventRepo: IEventRepo): MainViewModelFactory {
        return MainViewModelFactory(eventRepo)
    }
}