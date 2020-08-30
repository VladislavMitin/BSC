package ru.vladislavmitin.bsc.domain

interface IEventRepo {
    suspend fun getEvents(): Result<List<Event>>
}