package com.veenapilli.learningthroughoxford.features.common.slider

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.veenapilli.learningthroughoxford.R
import kotlinx.android.synthetic.main.fragment_screen_slide_page.*

class ScreenSlidePageFragment : Fragment() {
    private var pos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            pos = it?.getString("pos")!!.toInt()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_screen_slide_page, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nothing.setOnClickListener {
            Log.d("Slider", "Nothing clicked $pos")
        }

    }
}
