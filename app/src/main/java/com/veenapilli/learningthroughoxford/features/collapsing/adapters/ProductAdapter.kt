package com.veenapilli.learningthroughoxford.features.collapsing.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.veenapilli.learningthroughoxford.features.collapsing.view.interactions.ClickImageImpl
import com.veenapilli.learningthroughoxford.features.collapsing.poko.ProductEntry
import com.veenapilli.learningthroughoxford.features.collapsing.view.holders.ProductViewHolder

class ProductAdapter internal constructor(
    private var products: List<ProductEntry>,
    private var clickHandler: ClickImageImpl
) : RecyclerView.Adapter<ProductViewHolder>() {

    internal fun setProducts(products: List<ProductEntry>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductViewHolder {
        return ProductViewHolder(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: ProductViewHolder, i: Int) {
        viewHolder.bind(products[i], clickHandler)

    }

    override fun getItemCount(): Int {
        return products.size
    }
}