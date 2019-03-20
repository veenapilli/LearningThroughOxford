package com.veenapilli.learningthroughoxford.features.common.slider

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ScreenSlideAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val NUM_PAGES = 4

    override fun getCount(): Int = NUM_PAGES

    override fun getItem(position: Int) = ScreenSlidePageFragment().apply {
        arguments = Bundle().apply {
            putString("pos", position.toString())
        }
    }
}
