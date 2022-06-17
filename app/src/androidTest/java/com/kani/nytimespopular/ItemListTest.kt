package com.kani.nytimespopular

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kani.nytimespopular.ui.activity.ItemDetailHostActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ItemListTest {

    @get: Rule
    val mActivityRule = ActivityScenarioRule(ItemDetailHostActivity::class.java)

    @Before
    fun setup() {
        ActivityScenario.launch(ItemDetailHostActivity::class.java)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkIfListIsLoaded() = runBlocking {
        onView(withId(R.id.item_list)).check(matches(isDisplayed()))
        delay(2*1000)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun swipeRefreshTest() = runBlocking {
        delay(2*1000)
        onView(withId(R.id.swipeRefresh)).perform(swipeDown())
        delay(2*1000)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun menuClickTest() = runBlocking{
        delay(1*1000)
        openContextualActionModeOverflowMenu()
        onView(withText(R.string.menu_item_period7)).perform(click())
        delay(1*1000)
        mActivityRule.scenario.close()
    }

}