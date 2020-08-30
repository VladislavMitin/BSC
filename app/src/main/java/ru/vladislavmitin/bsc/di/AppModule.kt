package ru.vladislavmitin.bsc.di

import android.content.ContentResolver
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.vladislavmitin.bsc.app.App
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideAppContext(app: App): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideContentResolver(appContext: Context): ContentResolver {
        return appContext.contentResolver
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}