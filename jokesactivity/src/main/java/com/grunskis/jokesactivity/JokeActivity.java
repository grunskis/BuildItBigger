package com.grunskis.jokesactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "com.grunskis.jokesactivity.JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        TextView jokeTextView = findViewById(R.id.tv_joke);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_JOKE)) {
            String joke = intent.getStringExtra(EXTRA_JOKE);
            jokeTextView.setText(joke);
        } else {
            jokeTextView.setText(R.string.error_no_joke);
        }
    }
}
