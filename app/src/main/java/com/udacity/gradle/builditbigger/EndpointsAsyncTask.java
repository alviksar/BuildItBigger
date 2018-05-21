package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 *  Loads jokes from GCE module
 */

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String LOG_TAG = EndpointsAsyncTask.class.getSimpleName();

    // Pre and post execute tasks
    public interface edgedTasks {
        void preExecute();
        void postExecute(String result);
    }

    private final edgedTasks mEdgedTasks;

    private MyApi myApiService = null;

    EndpointsAsyncTask(edgedTasks edgedTasks) {
        this.mEdgedTasks = edgedTasks;
    }

    @Override
    protected String doInBackground(Void... voids) {

        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {

            return myApiService.tellJoke().execute().getData();

        } catch (IOException e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));
            return "";
//              return e.getMessage();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mEdgedTasks.preExecute();

    }

    @Override
    protected void onPostExecute(String result) {
        mEdgedTasks.postExecute(result);

    }
}
