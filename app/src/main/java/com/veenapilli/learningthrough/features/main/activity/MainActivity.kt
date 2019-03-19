package com.veenapilli.learningthrough.features.main.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.veenapilli.learningthrough.features.oxford.fragments.LandingFragment
import com.veenapilli.learningthrough.R
import com.veenapilli.learningthrough.features.collapsing.justactivitiy.CollapsibleToolbarActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true

            when (menuItem.itemId) {
                R.id.oxford -> {
                    swapFragment(LandingFragment())
                }
                R.id.collapse -> {
                    val intent = Intent(this, CollapsibleToolbarActivity::class.java)
                    startActivity(intent)
                }
            }
            // close drawer when item is tapped
            drawer_layout.closeDrawers()
            true
        }
    }

    private fun swapFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.main_content, fragment).commit()
    }
}