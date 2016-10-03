package com.example.glenn.criminal;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by Glenn on 26/08/2016.
 */
@RunWith(AndroidJUnit4.class)
public class CrimePagerActivityTest {

    @Rule
    private final IntentsTestRule<CrimePagerActivity> crimePager = new IntentsTestRule<CrimePagerActivity>(CrimePagerActivity.class);

    @Test
    public void testClickButtonCameraResultsInCameraIntent(){
        // Bitmap aanmaken dat we kunnen gebruiken om onze camera fotos te simuleren
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.ic_launcher);

        // Bouw een resultaat dat terugkomt van de camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(toPackage("com.android.camera2")).respondWith(result);


        onView(allOf(withId(R.id.crime_camera), isDisplayed())).perform(click());

        intended(toPackage("com.android.camera2"));
    }

    @Test
    public void testClickButtonSuspectResultsInContactsIntent() {
        String contactName = "Contact1";

        Intent resultData = new Intent();
        resultData.setData(getContactUriByName(contactName));
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(toPackage("com.android.contacts")).respondWith(result);

        onView(allOf(withId(R.id.crime_suspect), isDisplayed())).perform(click());

        onView(withId(R.id.crime_suspect))
                .check(matches(withText(contactName)));

        intended(toPackage("com.android.contacts"));
    }

    public Uri getContactUriByName(String contactName) {
        Cursor cursor = crimePager.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone
                .CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone
                        .DISPLAY_NAME));
                if (name.equals(contactName)) {
                    return Uri.withAppendedPath(ContactsContract.Data.CONTENT_URI, id);
                }
            }
        }
        return null;
    }
}
