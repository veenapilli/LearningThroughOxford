package com.veenapilli.learningthroughoxford.features.collapsing.withfragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.*
import com.veenapilli.learningthroughoxford.R
import com.veenapilli.learningthroughoxford.features.collapsing.view.interactions.ClickImageImpl
import com.veenapilli.learningthroughoxford.features.collapsing.poko.ProductEntry
import com.veenapilli.learningthroughoxford.features.collapsing.adapters.ProductAdapter
import kotlinx.android.synthetic.main.fragment_landing_collapsing.*

class CollapsibleToolbarFragment : Fragment() {
    private var adapter: ProductAdapter? = null
    private lateinit var products: ArrayList<ProductEntry>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_landing_collapsing, container, false)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.email, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(app_bar_activity)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        products = readProductsList()
    }

    private val clickHandler =
        ClickImageImpl()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        product_list_fragment.setHasFixedSize(true)
        product_list_fragment.layoutManager = GridLayoutManager(this.context, resources.getInteger(R.integer.column_count))
        adapter =
            ProductAdapter(products, clickHandler)
        product_list_fragment.adapter = adapter
    }

    private fun readProductsList(): ArrayList<ProductEntry> {
        val list = ArrayList<ProductEntry>()
        list.add(ProductEntry(title = "1", price = "22222"))
        list.add(ProductEntry(title = "11", price = "2222"))
        list.add(ProductEntry(title = "111", price = "222"))
        list.add(ProductEntry(title = "1111", price = "22"))
        list.add(ProductEntry(title = "11111", price = "2"))
        return list
    }


}
