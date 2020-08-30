package ru.vladislavmitin.bsc.di

import android.content.ContentResolver
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.vladislavmitin.bsc.data.*
import ru.vladislavmitin.bsc.domain.IEventRepo
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideEventRepo(eventService: IEventService, contactService: IContactService): IEventRepo {
        return EventRepo(eventService, contactService)
    }

    @Singleton
    @Provides
    fun provideEventService(appContext: Context, gson: Gson): IEventService {
        return EventService(appContext, gson)
    }

    @Singleton
    @Provides
    fun provideContactService(contentResolver: ContentResolver): IContactService {
        return ContactService(contentResolver)
    }
}