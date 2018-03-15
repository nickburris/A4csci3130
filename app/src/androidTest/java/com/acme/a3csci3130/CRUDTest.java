package com.acme.a3csci3130;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 * Tests all CRUD aspects of the application.
 */
@RunWith(AndroidJUnit4.class)
public class CRUDTest {
    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    private static final String testUid = "uid_test";
    private static final String testName = "TestBusiness";
    private static final String testBusinessNumber = "123456789";
    private static final String testPrimaryBusiness = "Distributor";
    private static final String testAddress = "101 Testy Ave";
    private static final String testProvince = "NS";

    private static final Business testBusiness = new Business(testUid, testBusinessNumber, testName,
            testPrimaryBusiness, testAddress, testProvince);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.acme.a3csci3130", appContext.getPackageName());
    }

    /**
     * Before each test, provide a test business in the database
     */
    @Before
    public void setupTestBusiness(){
        Task<Void> task = FirebaseDatabase.getInstance().getReference("businesses").child(testBusiness.uid).setValue(testBusiness);
        while(!task.isComplete());
    }

    /**
     * After each test, remove the test business from the database
     */
    @After
    public void deleteTestBusiness(){
        Task<Void> task = FirebaseDatabase.getInstance().getReference("businesses").child(testBusiness.uid).removeValue();
        while(!task.isComplete());
    }

    /**
     * Test creating a new business
     */
    @Test
    public void testCreateBusiness(){
        onView(withId(R.id.createBusinessButton)).perform(click());
        intended(hasComponent(CreateBusinessAcitivity.class.getName()));

        onView(withId(R.id.createNameField)).perform(typeText(testName + "New"));
        onView(withId(R.id.createBusinessNumberField)).perform(typeText(testBusinessNumber));
        onView(withId(R.id.createPrimaryBusinessField)).perform(typeText(testPrimaryBusiness));
        onView(withId(R.id.createAddressField)).perform(typeText(testAddress));
        onView(withId(R.id.createProvinceField)).perform(typeText(testProvince));
        closeSoftKeyboard();

        onView(withId(R.id.submitButton)).perform(click());

        // Verify the new business exists
        onView(withText(testName + "New")).perform(click());
        // Delete it to clean up
        onView(withId(R.id.deleteButton)).perform(click());
    }

    /**
     * Test that firebase rules are working by trying to create an invalid business
     */
    @Test
    public void testCreateInvalidBusiness(){
        onView(withId(R.id.createBusinessButton)).perform(click());

        onView(withId(R.id.createNameField)).perform(typeText(testName + "New"));
        onView(withId(R.id.createBusinessNumberField)).perform(typeText(testBusinessNumber));
        onView(withId(R.id.createPrimaryBusinessField)).perform(typeText(testPrimaryBusiness + "Nope"));
        onView(withId(R.id.createAddressField)).perform(typeText(testAddress));
        onView(withId(R.id.createProvinceField)).perform(typeText(testProvince));
        closeSoftKeyboard();

        onView(withId(R.id.submitButton)).perform(click());

        // Verify the new business did not actually get created
        try {
            onView(withText(testName + "New")).check(matches(isDisplayed()));
            fail();
        } catch (NoMatchingViewException e){
            // test business entry not found, test is successful
        }
    }

    /**
     * Test reading a business, try to click on the test business and read its data
     */
    @Test
    public void testReadBusiness(){
        onView(withText(testName)).perform(click());
        intended(hasComponent(DetailViewActivity.class.getName()));

        // check all fields are displayed
        onView(withText(testName)).check(matches(isDisplayed()));
        onView(withText(testBusinessNumber)).check(matches(isDisplayed()));
        onView(withText(testPrimaryBusiness)).check(matches(isDisplayed()));
        onView(withText(testAddress)).check(matches(isDisplayed()));
        onView(withText(testProvince)).check(matches(isDisplayed()));
    }

    /**
     * Test updating a business by changing the name and verifying the new name
     */
    @Test
    public void testUpdateBusiness(){
        onView(withText(testName)).perform(click());

        onView(withText(testName)).perform(typeText("Modified"));
        closeSoftKeyboard();

        onView(withId(R.id.updateButton)).perform(click());

        onView(withText(testName + "Modified")).check(matches(isDisplayed()));
    }

    /**
     * Test deleting a business
     */
    @Test
    public void testDeleteBusiness(){
        onView(withText(testName)).perform(click());
        onView(withId(R.id.deleteButton)).perform(click());
        try {
            onView(withText(testName)).check(matches(isDisplayed()));
            fail();
        } catch (NoMatchingViewException e){
            // test business entry not found, test is successful
        }
    }
}
