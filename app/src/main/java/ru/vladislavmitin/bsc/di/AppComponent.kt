package ru.vladislavmitin.bsc.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.vladislavmitin.bsc.app.App
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    DataModule::class,
    UiComponentModule::class
])
interface AppComponent: AndroidInjector<App> {
    @Component.Factory
    interface Factory: AndroidInjector.Factory<App>
}
