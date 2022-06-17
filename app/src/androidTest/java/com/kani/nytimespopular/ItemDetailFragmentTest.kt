package com.kani.nytimespopular

import android.view.View
import android.widget.RelativeLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kani.nytimespopular.ui.activity.ItemDetailHostActivity
import com.kani.nytimespopular.ui.adapter.ArticleListAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDetailFragmentTest {

    @get: Rule
    val mActivityRule = ActivityScenarioRule(ItemDetailHostActivity::class.java)

    @Before
    fun setup() {
        ActivityScenario.launch(ItemDetailHostActivity::class.java)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun navigateDetailsFragment() = runBlocking {
        delay(2*1000)

        Espresso.onView(ViewMatchers.withId(R.id.item_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ArticleListAdapter.ViewHolder>(
                3,
                object : ViewAction {
                    override fun getConstraints(): Matcher<View>? {
                        return ViewMatchers.isAssignableFrom(View::class.java)
                    }

                    override fun getDescription(): String {
                        return "test"
                    }

                    override fun perform(uiController: UiController?, view: View?) {
                        val rvView = view?.findViewById<RelativeLayout>(R.id.item_list_container)
                        rvView?.performClick()
                    }
                }
            )
        )

        delay(3*1000)

        mActivityRule.scenario.close()
    }
}