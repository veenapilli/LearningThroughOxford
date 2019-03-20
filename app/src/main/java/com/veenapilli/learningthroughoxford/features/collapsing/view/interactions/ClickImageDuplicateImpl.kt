package com.veenapilli.learningthroughoxford.features.collapsing.view.interactions

import android.util.Log

class ClickImageDuplicateImpl :
    ClickHandler {
    override fun onClickRecyclerItem() {
        Log.d("Click Recycler", "Recycler")
    }

    override fun onClickText() {
        Log.d("Click Text", "Sample")
    }

    override fun onClickImage() {
        Log.d("Click Image", "Clicked")
    }

    override fun onClickBottomNav() {
        Log.d("Click Nav", "Nav")
    }

}