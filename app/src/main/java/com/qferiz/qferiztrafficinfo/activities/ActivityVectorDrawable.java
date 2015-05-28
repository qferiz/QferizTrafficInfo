package com.qferiz.qferiztrafficinfo.activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.qferiz.qferiztrafficinfo.R;
import com.qferiz.qferiztrafficinfo.extras.Util;
import com.telly.mrvector.MrVector;


public class ActivityVectorDrawable extends AppCompatActivity {

    private ImageView imageView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_test);

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

        imageView = (ImageView) findViewById(R.id.vectorImage);
        Drawable drawable = null;

        /** CEK OS ANDROID VERSI SDK */

        if (Util.isLollipopOrGreater()) {
            drawable = MrVector.inflate(getResources(), R.drawable.animator_vector_clock);
        } else {
            drawable = MrVector.inflate(getResources(), R.drawable.vector_clock);
        }

        if (Util.isJellyBeanOrGreater()) {
            imageView.setBackground(drawable);
        } else {
            imageView.setBackgroundDrawable(drawable);
        }

        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vector_test, menu);
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
}
