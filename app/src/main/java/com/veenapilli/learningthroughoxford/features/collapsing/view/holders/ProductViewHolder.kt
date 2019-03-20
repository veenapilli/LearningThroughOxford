package com.veenapilli.learningthroughoxford.features.collapsing.view.holders

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.veenapilli.learningthroughoxford.R
import com.veenapilli.learningthroughoxford.features.collapsing.view.interactions.ClickHandler
import com.veenapilli.learningthroughoxford.features.collapsing.poko.ProductEntry

class ProductViewHolder internal constructor(parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product, parent, false)) {
    private val priceView
            = itemView.findViewById(R.id.price) as TextView

    private val clickListener = View.OnClickListener {
    }

    init {
        itemView.setOnClickListener(clickListener)
    }


    internal fun bind(
        product: ProductEntry,
        clickHandler: ClickHandler?
    ) {
        priceView.text = product.price

        priceView.setOnClickListener {
            Log.d("Recycler", "Price")
            clickHandler?.onClickRecyclerItem()
        }
    }
}