package com.veenapilli.learningthroughoxford.features.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.veenapilli.learningthroughoxford.R
import com.veenapilli.learningthroughoxford.features.collapsing.justactivitiy.CollapsibleToolbarActivity
import com.veenapilli.learningthroughoxford.features.collapsing.view.interactions.ClickHandler
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollapsibleToolbarActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CollapsibleToolbarActivity::class.java)

    @MockK
    lateinit var click: ClickHandler

    private lateinit var galleryView: View

    @org.junit.Before
    fun setUp() {
        val activity = activityRule.activity
        galleryView = activity.findViewById(R.id.prop_details_gallery_container)

        click = mockk(relaxed = true)
    }

    @org.junit.After
    fun tearDown() {
    }

    @Test
    fun that_on_click_of_image_exception_is_thrown() {
        onView(withId(R.id.image_2)).perform(click())
        verify {
            click.onClickImage()
        }
    }

    @Test
    fun that_on_click_of_recycler_item_action_is_called() {
        onView(withId(R.id.product_list_activity)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, customClick(R.id.price, 0)))
    }



    private fun customClick(id: Int, index: Int) = object : ViewAction {
        override fun getDescription() = null
        override fun getConstraints() = null

        override fun perform(uiController: UiController?, view: View?) {
            (view as ViewGroup).let {
                if (view.childCount > index) {
                    val viewToClick = view.getChildAt(index)
                    click().perform(uiController, viewToClick.findViewById(id))
                }
            }
        }
    }

    @Test
    fun that_on_bottom_nav_click() {
        onView(withId(R.id.bottom_nav)).perform(click())
    }

}