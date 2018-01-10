package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.grunskis.jokesactivity.JokeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private BackendClient mBackendClient;

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(
            MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(isInternal()).respondWith(new Instrumentation.ActivityResult(
                Activity.RESULT_OK, null));
    }

    @Before
    public void registerIdlingResource() {
        mBackendClient = new BackendClient();
        mBackendClient.setUnderEspresso();
        Espresso.registerIdlingResources(mBackendClient);
    }

    @Test
    public void tellJoke_getJoke_launchJokeActivity() {
        mBackendClient.execute(mActivityRule.getActivity());

        intended(allOf(hasComponent(JokeActivity.class.getName()),
                hasExtraWithKey(JokeActivity.EXTRA_JOKE)));
    }

    @After
    public void unregisterIdlingResource() {
        if (mBackendClient != null) {
            Espresso.unregisterIdlingResources(mBackendClient);
        }
    }
}
