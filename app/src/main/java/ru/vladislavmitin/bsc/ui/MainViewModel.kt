package ru.vladislavmitin.bsc.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay
import ru.vladislavmitin.bsc.domain.Event
import ru.vladislavmitin.bsc.domain.IEventRepo
import ru.vladislavmitin.bsc.domain.Status
import java.lang.Exception

class MainViewModel(private val eventRepo: IEventRepo): ViewModel() {
    var loading = MutableLiveData<Boolean>(false)
    var error = MutableLiveData<String>()

    val currentEvent: MutableLiveData<Event?> = MutableLiveData(null)

    var events = liveData {
        try {
            loading.postValue(true)
            delay(2000) //emulate loading
            val eventsResult = eventRepo.getEvents()

            when(eventsResult.status) {
                Status.SUCCESS -> {
                    emit(eventsResult.data)
                }
                Status.ERROR -> {
                    eventsResult.error?.let {
                        error.postValue(it)
                    }
                }
            }
        } catch (ex: Exception) {
            error.postValue(ex.message)
        } finally {
            loading.postValue(false)
        }
    }

    fun setCurrentEvent(id: Int) {
        currentEvent.value = events.value?.find { it.id == id }
    }
}