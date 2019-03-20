package com.veenapilli.learningthroughoxford.features.main.application

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class LearningApplication: Application() {
    val TAG = LearningApplication::class.java.name
    override fun onCreate() {
        super.onCreate()
        if (FirebaseApp.getApps(this).isEmpty()) {
            Log.d(TAG, "Initialized Firebase app.")
            FirebaseApp.initializeApp(this)
        }
    }
}