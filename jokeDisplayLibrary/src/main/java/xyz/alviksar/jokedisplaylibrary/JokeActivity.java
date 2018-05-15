package xyz.alviksar.jokedisplaylibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Shows a joke passed to it as an intent extra.
 */

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_TEXT_EXTRA = "joke_text_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        TextView jokeTextView = findViewById(R.id.tv_joke);
        jokeTextView.setText(getIntent().getStringExtra(JOKE_TEXT_EXTRA));
    }
}
