package com.veenapilli.learningthroughoxford.features.main

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.veenapilli.learningthroughoxford.R

class LandingActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_test)
        supportFragmentManager.beginTransaction().add(R.id.main_content, LandingFragment()).commit()
    }
}
