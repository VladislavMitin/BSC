package ru.vladislavmitin.bsc.data

import ru.vladislavmitin.bsc.domain.Event
import ru.vladislavmitin.bsc.domain.IEventRepo
import ru.vladislavmitin.bsc.domain.Result
import ru.vladislavmitin.bsc.domain.Status

class EventRepo(private val eventService: IEventService, private val contactService: IContactService): IEventRepo {
    override suspend fun getEvents(): Result<List<Event>> {
        val eventsDto = eventService.getEvents() ?: return Result(Status.ERROR, error = "Error")

        val events = eventsDto.map { it.toModel() }

        events.forEach { event ->
            event.invited.image = contactService.getPhoto(event.invited.phone)

            event.guests?.forEach { guest ->
               guest.image = contactService.getPhoto(guest.phone)
            }
        }

        return Result(Status.SUCCESS, events)
    }
}