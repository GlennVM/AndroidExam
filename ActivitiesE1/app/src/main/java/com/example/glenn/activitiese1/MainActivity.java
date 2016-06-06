package com.example.glenn.activitiese1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int onCreate = 0;
    private int onStart = 0;
    private int onResume = 0;
    private int onPause = 0;
    private int onStop = 0;
    private int onDestroy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        onCreate++;
        updateTextView();
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

    @Override
    protected void onStart() {
        onStart++;
        updateTextView();
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        onResume++;
        updateTextView();
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        onPause++;
        updateTextView();
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        onStop++;
        updateTextView();
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        onDestroy++;
        updateTextView();
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    protected void updateTextView() {
        setContentView(R.layout.activity_main);
        TextView myTextView = (TextView) findViewById(R.id.counter);
        myTextView.setText("onCreate(): " + onCreate + "\nonStart(): " + onStart + "\nonResume(): " + onResume + "\nonPause(): " + onPause + "\nonStop(): " + onStop + "\nonDestroy(): " + onDestroy);
    }

}
