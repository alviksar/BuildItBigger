package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import xyz.alviksar.jokedisplaylibrary.JokeActivity;

public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.edgedTasks {

    public static final String MAIN_ACTIVITY_IDLING_RESOURCE_NAME
            = "main_activity_idling_resource_name";

    // The Idling Resource which will be null in production.
    @Nullable
    CountingIdlingResource mIdlingResource = null;

    ProgressBar progressBar;

    AdView mAdView;

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.pb_wait_joke);

        mAdView = findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        // Load interstitial ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                new EndpointsAsyncTask(MainActivity.this).execute();
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });

        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        Button tellJokeButton = findViewById(R.id.btn_tell_joke);

        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.e("TAG", "The interstitial wasn't loaded yet.");
                }

               // new EndpointsAsyncTask(MainActivity.this).execute();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void startShowJokeActivity(String joke) {
        // Start an intent
        Intent intent = new Intent(MainActivity.this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_TEXT_EXTRA, joke);
        startActivity(intent);
    }

    @Override
    public void preExecute() {
        if (mIdlingResource != null)
            mIdlingResource.increment();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(String result) {
        if (mIdlingResource != null)
            mIdlingResource.decrement();
        progressBar.setVisibility(View.GONE);
        startShowJokeActivity(result);

    }

    /**
     * Only called from test. Creates and returns a new CountingIdlingResource.
     */
    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource
                    = new CountingIdlingResource(MainActivity.MAIN_ACTIVITY_IDLING_RESOURCE_NAME);
        }
        return mIdlingResource;

    }

    /**
     *  For test interstitial ad view.
     */
    @VisibleForTesting
    AdView getAdView() {
        return mAdView;
    }
}
