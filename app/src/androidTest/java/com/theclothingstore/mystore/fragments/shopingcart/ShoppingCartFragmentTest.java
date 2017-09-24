package com.theclothingstore.mystore.fragments.shopingcart;

import android.app.FragmentTransaction;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.theclothingstore.mystore.ContentActivity;
import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.model.CartItem;
import com.theclothingstore.mystore.model.Product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Locale;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.theclothingstore.mystore.helper.Constants.UnicodeConstants.POUNDS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Anand Soni
 */
public class ShoppingCartFragmentTest {

    @Rule
    public ActivityTestRule<ContentActivity> activityTestRule = new ActivityTestRule<>(ContentActivity.class);

    private ShoppingCartFragment fragment;

    @Before
    public void setUp() {
        fragment = ShoppingCartFragment.newInstance();
        ContentActivity activity = activityTestRule.getActivity();
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        transaction.replace(ContentActivity.CONTAINER_ID, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    @Test
    public void testNewInstance() {
        assertNotNull(fragment);
    }

    @Test
    public void testOnCreateView() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                fragment.onCreate(null);
                LayoutInflater layoutInflater = LayoutInflater.from(InstrumentationRegistry.getTargetContext());
                fragment.onCreateView(layoutInflater, null, null);
            }
        });
        assertNotNull(fragment.getCartList());
        assertNotNull(fragment.getAdapter());
        assertEquals(fragment.getAdapter(), fragment.getCartList().getAdapter());
    }

    @Test
    public void testToolbar() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withText(R.string.shopping_cart)).check(matches(withParent(withId(R.id.toolbar))));
        onView(withId(R.id.shopping_cart)).check(doesNotExist());
        onView(withId(R.id.wish_list)).check(doesNotExist());
    }

    @Test
    public void testUserInterface() {
        onView(withId(R.id.cart_list)).check(matches(isDisplayed()));
        //testing the RecyclerView item at position 0
        onView(withId(R.id.cart_list)).perform(RecyclerViewActions.scrollToPosition(0)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                CartItem cartItem = mockCartItem();
                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.ViewHolder viewHolderForPosition = recyclerView.findViewHolderForLayoutPosition(0);
                View viewAtPosition = viewHolderForPosition.itemView;
                matches(hasDescendant(withText(cartItem.getProduct().getProductName()))).check(viewAtPosition, e);
                String priceText = String.format(Locale.getDefault(), "%s%s", POUNDS, String.valueOf(cartItem.getProduct().getProductPrice()));
                matches(hasDescendant(withText(priceText))).check(viewAtPosition, e);
                matches(hasDescendant(withId(R.id.button_remove_cart))).check(viewAtPosition, e);
            }
        });
    }

    private CartItem mockCartItem() {
        Product product = new Product();
        product.setProductName("Almond Toe Court Shoes, Patent Black");
        product.setProductPrice(99);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCartId(1);
        return cartItem;
    }

}