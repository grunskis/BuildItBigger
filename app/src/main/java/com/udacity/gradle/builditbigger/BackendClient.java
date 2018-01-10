package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class BackendClient extends AsyncTask<BackendClient.Callback, Void, String>
        implements IdlingResource {

    private MyApi mApi = null;
    private Callback mCallback = null;
    private boolean mUnderEspresso = false;
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);
    private volatile ResourceCallback mResourceCallback;

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }

    void setUnderEspresso() {
        mUnderEspresso = true;
    }

    @Override
    protected void onPreExecute() {
        if (mUnderEspresso) {
            setIdleState(false);
        }
    }

    @Override
    protected String doInBackground(Callback... callbacks) {
        mCallback = callbacks[0];

        if (mApi == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserverb
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            mApi = builder.build();
        }

        try {
            return mApi.getJoke().execute().getJoke();
        } catch (IOException e) {
            Log.e(BackendClient.class.getSimpleName(), e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        if (mCallback != null) {
            mCallback.onDone(joke);
            if (mUnderEspresso) {
                setIdleState(true);
            }
        }
    }

    private void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mResourceCallback != null) {
            mResourceCallback.onTransitionToIdle();
        }
    }

    interface Callback {
        void onDone(String result);
    }
}
