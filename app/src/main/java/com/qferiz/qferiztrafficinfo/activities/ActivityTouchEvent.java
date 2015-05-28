package com.qferiz.qferiztrafficinfo.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.qferiz.qferiztrafficinfo.R;


public class ActivityTouchEvent extends AppCompatActivity {

    public static final String TAG = "QFERIZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        setupToolbar();
    }

    private void setupToolbar(){
        //Set the Toolbar as ActionBar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.app_name);
            setSupportActionBar(mToolbar);
            assert getSupportActionBar() != null;
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Supaya tombol back / logo bisa di tekan/pressable
            getSupportActionBar().setHomeButtonEnabled(true);
            //getSupportActionBar().setIcon(R.mipmap.ic_launcher); // set Icon / Logo Apps
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
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

        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "MySubActivity dispatchTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "MySubActivity dispatchTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "MySubActivity dispatchTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "MySubActivity dispatchTouchEvent CANCEL");
                break;
        }

        boolean b = super.dispatchTouchEvent(ev);
        Log.d(TAG, "MySubActivity dispatchTouchEvent RETURN " + b);
        //return b;
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "MySubActivity onTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "MySubActivity onTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "MySubActivity onTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "MySubActivity onTouchEvent CANCEL");
                break;

        }

        boolean b = super.onTouchEvent(event);
        Log.d(TAG, "MySubActivity onTouchEvent RETURN " + b);
        return b;
    }


}
