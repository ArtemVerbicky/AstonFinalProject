package ru.verb.astonfinalproject.presentation.application

import android.app.Application
import android.content.Context
import ru.verb.astonfinalproject.presentation.di.AppComponent
import ru.verb.astonfinalproject.presentation.di.DaggerAppComponent

val Context.appComponent: AppComponent
    get() = when(this) {
        is AstonFinalApp -> appComponent
        else -> this.applicationContext.appComponent
    }

class AstonFinalApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}