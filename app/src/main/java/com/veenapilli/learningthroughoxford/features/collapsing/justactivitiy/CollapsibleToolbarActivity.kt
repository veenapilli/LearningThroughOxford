package com.veenapilli.learningthroughoxford.features.collapsing.justactivitiy

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import com.veenapilli.learningthroughoxford.R
import com.veenapilli.learningthroughoxford.features.common.slider.ScreenSlideAdapter
import com.veenapilli.learningthroughoxford.features.collapsing.view.interactions.ClickImageDuplicateImpl
import com.veenapilli.learningthroughoxford.features.collapsing.view.interactions.ClickImageImpl
import com.veenapilli.learningthroughoxford.features.collapsing.adapters.ProductAdapter
import com.veenapilli.learningthroughoxford.features.collapsing.poko.ProductEntry
import kotlinx.android.synthetic.main.activity_landing_collapsing_toolbar.*

class CollapsibleToolbarActivity : AppCompatActivity() {
    private var adapter: ProductAdapter? = null
    private lateinit var prods: ArrayList<ProductEntry>

    private var clickHandler =
        ClickImageImpl()

    private lateinit var pager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_landing_collapsing_toolbar)

        setSupportActionBar(app_bar_activity)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        prods = readProductsList()


        // Instantiate a ViewPager and a PagerAdapter.
        pager = findViewById(R.id.pager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter =
            ScreenSlideAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter

        product_list_activity.setHasFixedSize(true)
        product_list_activity.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.column_count))
        adapter = ProductAdapter(prods, clickHandler)
        product_list_activity.adapter = adapter


        bottom_nav.setOnClickListener {
            clickHandler.onClickBottomNav()
        }


        var list: List<Any> = mutableListOf(
            ClickImageImpl(),
            ClickImageDuplicateImpl()
        )
        list.forEachIndexed { index, any ->
            if (any is ClickImageImpl) {
                Log.d("List", "ClickImageImpl $index")
            } else {
                Log.d("List", "ClickImageDuplicateImpl $index")
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.email, menu)
        return true
    }


    private fun readProductsList(): ArrayList<ProductEntry> {
        val list = ArrayList<ProductEntry>()
        list.add(ProductEntry(title = "1", price = "22222"))
        list.add(ProductEntry(title = "11", price = "2222"))
        list.add(ProductEntry(title = "111", price = "222"))
        list.add(ProductEntry(title = "1111", price = "22"))
        list.add(ProductEntry(title = "11111", price = "2"))
        list.add(ProductEntry(title = "1111", price = "22"))
        list.add(ProductEntry(title = "111", price = "222"))
        list.add(ProductEntry(title = "11", price = "2222"))
        list.add(ProductEntry(title = "1", price = "22222"))
        return list
    }


}
