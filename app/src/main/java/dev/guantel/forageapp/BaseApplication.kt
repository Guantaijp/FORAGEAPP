package dev.guantel.forageapp

import android.app.Application
import dev.guantel.forageapp.data.ForageDatabase

class BaseApplication : Application() {
    val database : ForageDatabase by lazy {
        ForageDatabase.getDatabase(this)
    }
}