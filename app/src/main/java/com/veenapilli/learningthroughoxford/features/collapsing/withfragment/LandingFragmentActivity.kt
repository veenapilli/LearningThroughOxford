package com.veenapilli.learningthroughoxford.features.collapsing.withfragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.veenapilli.learningthroughoxford.R
import com.veenapilli.learningthroughoxford.features.collapsing.poko.ProductEntry
import com.veenapilli.learningthroughoxford.features.collapsing.adapters.ProductAdapter

class LandingFragmentActivity : AppCompatActivity() {
    private var adapter: ProductAdapter? = null
    private lateinit var prods: ArrayList<ProductEntry>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_test)
        supportFragmentManager.beginTransaction().add(R.id.main_content,
            CollapsibleToolbarFragment()
        ).commit()

    }
}
