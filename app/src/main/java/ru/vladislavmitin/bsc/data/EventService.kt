package ru.vladislavmitin.bsc.data

import android.content.Context
import com.google.gson.Gson
import ru.vladislavmitin.bsc.R
import java.io.InputStreamReader

class EventService(private val appContext: Context, private val gson: Gson): IEventService {
    override suspend fun getEvents(): List<EventDto>? {
        val reader = InputStreamReader(appContext.resources.openRawResource(R.raw.events))

        return try {
            gson.fromJson<EventsDto>(reader, EventsDto::class.java).events
        } catch (ex: Exception) {
            null
        }
    }
}