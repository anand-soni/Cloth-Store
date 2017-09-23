package com.theclothingstore.mystore.fragments.productcatalogue;

import android.app.FragmentTransaction;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.theclothingstore.mystore.ContentActivity;
import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.model.Product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author Anand Soni
 */
public class ProductCatalogueFragmentTest {

    @Rule
    public ActivityTestRule<ContentActivity> activityTestRule = new ActivityTestRule<>(ContentActivity.class);

    private ProductCatalogueFragment fragment;
    private ContentActivity activity;

    @Before
    public void setUp() {
        fragment = ProductCatalogueFragment.newInstance();
        activity = activityTestRule.getActivity();
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        transaction.replace(ContentActivity.CONTAINER_ID, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    @Test
    public void testToolbar() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.toolbar))));
    }

    @Test
    public void testUserInterface() {
        onView(withId(R.id.product_list)).check(matches(isDisplayed()));
        //testing the RecyclerView item at position 0
        onView(withId(R.id.product_list)).perform(RecyclerViewActions.scrollToPosition(0)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                Product product = mockProduct();
                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.ViewHolder viewHolderForPosition = recyclerView.findViewHolderForLayoutPosition(0);
                View viewAtPosition = viewHolderForPosition.itemView;
                matches(hasDescendant(withText(product.getName()))).check(viewAtPosition, e);
                matches(hasDescendant(withText(product.getCategory()))).check(viewAtPosition, e);
                matches(hasDescendant(withText(String.valueOf(product.getStock())))).check(viewAtPosition, e);
            }
        });
    }

    private Product mockProduct() {
        Product product = new Product();
        product.setName("Almond Toe Court Shoes, Patent Black");
        product.setCategory("Women's Footwear");
        product.setPrice(99);
        product.setOldPrice(null);
        product.setStock(5);
        return product;
    }
}