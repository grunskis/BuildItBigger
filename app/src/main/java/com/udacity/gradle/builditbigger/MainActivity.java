package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.grunskis.jokesactivity.JokeActivity;


public class MainActivity extends AppCompatActivity implements BackendClient.Callback {

    private Button mButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.b_tell_joke);
        mProgressBar = findViewById(R.id.pb_loading);

        findViewById(R.id.b_tell_joke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.setEnabled(false);
                mProgressBar.setVisibility(View.VISIBLE);

                tellJoke(view);
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

    public void tellJoke(View view) {
        new BackendClient().execute(this);
    }

    @Override
    public void onDone(String result) {
        Intent intent = new Intent(this, JokeActivity.class);
        if (result != null) {
            intent.putExtra(JokeActivity.EXTRA_JOKE, result);
        }
        startActivity(intent);

        mButton.setEnabled(true);
        mProgressBar.setVisibility(View.INVISIBLE);

    }
}
