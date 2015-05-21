package com.qferiz.qferiztrafficinfo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.qferiz.qferiztrafficinfo.R;
import com.qferiz.qferiztrafficinfo.adapters.AdapterRecyclerItemAnimations;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class ActivityRecyclerItemAnimations extends AppCompatActivity {

    //int containing the duration of the animation run when items are added or removed from the RecyclerView
    public static final int ANIMATION_DURATION = 2000;
    private EditText mInput;
    private RecyclerView mRecyclerView;
    private AdapterRecyclerItemAnimations mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_item_animations);

        //Set the Toolbar as ActionBar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.app_name);
            setSupportActionBar(mToolbar);
            assert getSupportActionBar() != null;
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Supaya tombol back / logo bisa di tekan/pressable
            getSupportActionBar().setIcon(R.mipmap.ic_launcher); // set Icon / Logo Apps
        }

        mInput = (EditText) findViewById(R.id.text_input);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerAnimatedItems);
        mAdapter = new AdapterRecyclerItemAnimations(this);
//        DefaultItemAnimator animator = new DefaultItemAnimator();
        ScaleInAnimator animator = new ScaleInAnimator();
        animator.setAddDuration(ANIMATION_DURATION);
        animator.setRemoveDuration(ANIMATION_DURATION);

        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void addItem(View view) {
        if (mInput.getText() != null) {
            String text = mInput.getText().toString();
            if (text != null && text.trim().length() > 0) {
                mAdapter.addItem(mInput.getText().toString());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_recycler_item_animations, menu);
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

        if (android.R.id.home == id) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
