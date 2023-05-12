package com.villadevs.busschedule

import android.app.Application
import com.villadevs.busschedule.database.AppDatabase

class BusScheduleApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}