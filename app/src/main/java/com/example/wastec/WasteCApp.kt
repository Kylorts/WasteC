package com.example.wastec

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

class WasteCApp {
    @HiltAndroidApp
    class WasteCApp : Application()
}