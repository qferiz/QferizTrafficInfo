package com.qferiz.qferiztrafficinfo.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.qferiz.qferiztrafficinfo.R;
import com.qferiz.qferiztrafficinfo.fragments.MyFragment;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class ActivityUsingTabLibrary extends AppCompatActivity implements MaterialTabListener {

    private MaterialTabHost tabHost;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_tab_library);

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

        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabHost.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < adapter.getCount(); i++) {
            // TAB TEXT
            //tabHost.addTab(tabHost.newTab().setText(adapter.getPageTitle(i)).setTabListener(this));

            // TAB ICON
            tabHost.addTab(tabHost.newTab().setIcon(adapter.getIcon(i)).setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_using_tab_library, menu);
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
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        int icons[] = {R.drawable.ic_notifications_white_36dp,
                R.drawable.ic_map_white_36dp,
                R.drawable.ic_directions_white_36dp};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int num) {
            return MyFragment.getInstance(num);
        }

        @Override
        public int getCount() {
            return 3; // Jumlah TAB
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int position) {
            //return getResources().getDrawable(icons[position]);
            return ContextCompat.getDrawable(getApplicationContext(), icons[position]);
        }
    }

}
