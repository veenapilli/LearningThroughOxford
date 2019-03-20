package com.veenapilli.learningthroughoxford.features.main.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.veenapilli.learningthroughoxford.R
import com.veenapilli.learningthroughoxford.features.collapsing.justactivitiy.CollapsibleToolbarActivity
import com.veenapilli.learningthroughoxford.features.firebase.Messaging
import com.veenapilli.learningthroughoxford.features.oxford.fragments.LandingFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = MainActivity::class.java.name
        super.onCreate(savedInstanceState)

        // region app link

        val appLinkIntent = intent
        val appLinkAction = appLinkIntent.action
        val appLinkData = appLinkIntent.data
        val context = this
        //TODO: indexing: https://developer.android.com/studio/write/app-link-indexing
        // endregion

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

        // Get firebase Token

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                Log.d(TAG, "Token: $token")

            })

        doAsync {
            Messaging().getAccessToken(context)
        }

    }

    private fun swapFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.main_content, fragment).commit()
    }
}