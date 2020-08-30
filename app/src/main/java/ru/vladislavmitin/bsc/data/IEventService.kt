package ru.vladislavmitin.bsc.data

interface IEventService {
    suspend fun getEvents(): List<EventDto>?
}