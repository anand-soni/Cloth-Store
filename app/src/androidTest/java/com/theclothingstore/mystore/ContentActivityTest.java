package com.theclothingstore.mystore;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.content.ContextCompat;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

/**
 * @author Anand Soni
 */
public class ContentActivityTest {

    @Rule
    public ActivityTestRule<ContentActivity> activityTestRule = new ActivityTestRule<>(ContentActivity.class);

    private ContentActivity activity;

    @Before
    public void setUp() {
        activity = activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void testToolbar() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.toolbar))));
        onView(withId(R.id.toolbar)).check(matches(withToolbarBackGroundColor()));
    }

    @Test
    public void getShopService() {
        assertNotNull(activity.getShopService());
    }

    @Test
    public void getMyStoreApplication() {
        assertNotNull(activity.getMyStoreApplication());
    }

    @Test
    public void getNavigationController() {
        assertNotNull(activity.getNavigationController());
    }

    private Matcher<? super View> withToolbarBackGroundColor() {
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public boolean matchesSafely(View view) {
                final ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
                int primaryColor = ContextCompat.getColor(activityTestRule.getActivity(), R.color.colorPrimary);
                int toolbarColor = colorDrawable.getColor();
                return primaryColor == toolbarColor;
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }

}