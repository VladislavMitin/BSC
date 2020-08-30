package ru.vladislavmitin.bsc.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vladislavmitin.bsc.domain.IEventRepo

class MainViewModelFactory(private val eventRepo: IEventRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(eventRepo) as T
    }
}