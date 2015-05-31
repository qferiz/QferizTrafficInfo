package com.qferiz.qferiztrafficinfo.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.qferiz.qferiztrafficinfo.R;
import com.qferiz.qferiztrafficinfo.extras.SortListener;
import com.qferiz.qferiztrafficinfo.fragments.FragmentBoxOffice;
import com.qferiz.qferiztrafficinfo.fragments.FragmentDrawer;
import com.qferiz.qferiztrafficinfo.fragments.FragmentSearch;
import com.qferiz.qferiztrafficinfo.fragments.FragmentUpcoming;
import com.qferiz.qferiztrafficinfo.logging.L;
import com.qferiz.qferiztrafficinfo.services.ServiceMoviesBoxOffice;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * Created by Qferiz on 22/03/2015.
 * Upload Project ke GitHub 16/05/2015
 */

public class ActivityMain extends AppCompatActivity implements MaterialTabListener, View.OnClickListener {

    //int representing our 0th tab corresponding to the Fragment where search results are dispalyed
    public static final int TAB_SEARCH_RESULT = 0;
    //int corresponding to our 1st tab corresponding to the Fragment where box office hits are dispalyed
    public static final int TAB_HITS = 1;
    //int corresponding to our 2nd tab corresponding to the Fragment where upcoming movies are displayed
    public static final int TAB_UPCOMING = 2;
    //int corresponding to the number of tabs in our Activity
    private static final int TAB_COUNT = 3;
    //int corresponding to the id of our JobSchedulerService
    private static final int JOB_ID = 100;
    //tag associated with the FAB menu button that sorts by name
    private static final String TAG_SORT_NAME = "sortName";
    //tag associated with the FAB menu button that sorts by date
    private static final String TAG_SORT_DATE = "sortDate";
    //tag associated with the FAB menu button that sorts by ratings
    private static final String TAG_SORT_RATINGS = "sortRatings";
    //Run the JobSchedulerService every 2 minutes
    private static final long POLL_FREQUENCY = 28800000; // 28800 seconds - 480 menit - 8 jam
    private JobScheduler mJobScheduler;
    private ViewPager mPager;
    private MaterialTabHost mTabHost;
    private ViewPagerAdapter mAdapter;
    private FloatingActionButton mFAB;
    private FloatingActionMenu mFABMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDrawer();
        setupTabs();
        setupJob();

        // CircularFloatingActionMenu Button
        setupFAB();

        /*
        mTabs = (SlidingTabLayout) findViewById(R.id.com.qferiz.qferiztrafficinfo.tabs);
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        mTabs.setDistributeEvenly(true);

        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        mTabs.setViewPager(mPager);
        */

        // Setting jenis custom font dari folder Asset/fonts
        //Typefaces.get(this, "roboto-medium.ttf");
        //TextView myListText = (TextView) findViewById(R.id.listText); // listText font pada Navigation Drawer
        //Font.ROBOTO_MEDIUM.apply(this, myListText);

        /*
        Typeface font = Typeface.createFromAsset(myListText.getResources().getAssets(),"fonts/Roboto-Medium.ttf");
        myListText = (TextView) findViewById(R.id.listText);
        myListText.setTypeface(font);
        */

        /*
        TextView myListText = (TextView) findViewById(R.id.listText);
        String fontPath = "fonts/Roboto-Medium.ttf";
        Typeface tf = Typeface.createFromAsset(MainActivity.this.getResources().getAssets(), fontPath);
        myListText.setTypeface(tf);
        */
    }

    private void setupDrawer() {
        //Set the Toolbar as ActionBar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.app_name);
            setSupportActionBar(mToolbar);
//            assert getSupportActionBar() != null;
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeButtonEnabled(true);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Supaya tombol back / logo bisa di tekan/pressable
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
            //getSupportActionBar().setIcon(R.mipmap.ic_launcher); // set Icon / Logo Apps
        }

        //setup the NavigationDrawer
        FragmentDrawer drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

    }

    private void setupTabs() {
        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        //when the page changes in the ViewPager, update the Tabs accordingly
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
//                super.onPageSelected(position);
                mTabHost.setSelectedNavigationItem(position);
            }
        });

        //Add all the Tabs to the TabHost
        for (int i = 0; i < mAdapter.getCount(); i++) {
            // TAB TEXT
            //mTabHost.addTab(mTabHost.newTab().setText(mAdapter.getPageTitle(i)).setTabListener(this));

            // TAB ICON
            mTabHost.addTab(mTabHost.newTab().setIcon(mAdapter.getIcon(i)).setTabListener(this));
        }
    }

    private void setupJob() {
        mJobScheduler = JobScheduler.getInstance(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //schedule the job after the delay has been elapsed
                buildJob();
            }
        }, 30000); // 3 second / 30000 milisecond

    }

    private void buildJob() {
        //attach the job ID and the name of the Service that will work in the background
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, ServiceMoviesBoxOffice.class));

        //set periodic polling that needs net connection and works across device reboots
        builder.setPeriodic(POLL_FREQUENCY)
                //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // network any = wifi & data operator
                .setRequiresCharging(true)
                .setPersisted(true);
        mJobScheduler.schedule(builder.build());
    }

    public void onDrawerItemClicked(int index) {
        Log.d("QFERIZ", "onDrawerItemClicked, Index :" + index);
        mPager.setCurrentItem(index);
    }

    private void setupFAB() {
        // CircularFloatingActionMenu Button
        //define the icon for the main floating action button
        ImageView iconFAB = new ImageView(this);
        iconFAB.setImageResource(R.drawable.ic_action_new);


        // Create a button to attach the menu:
        //set the appropriate background for the main floating action button along with its icon
        mFAB = new FloatingActionButton.Builder(this)
                .setContentView(iconFAB)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        // Create menu items:
        //define the icons for the sub action buttons
        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_notifications_white_36dp);
        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_map_white_36dp);
        ImageView iconSortRatings = new ImageView(this);
        iconSortRatings.setImageResource(R.drawable.ic_directions_white_36dp);

        //set the background for all the sub buttons
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.selector_sub_button_gray));

        //build the sub buttons
        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();

        //to determine which button was clicked, set Tags on each button
        buttonSortName.setTag(TAG_SORT_NAME);
        buttonSortDate.setTag(TAG_SORT_DATE);
        buttonSortRatings.setTag(TAG_SORT_RATINGS);

        buttonSortName.setOnClickListener(this);
        buttonSortDate.setOnClickListener(this);
        buttonSortRatings.setOnClickListener(this);

        //add the sub buttons to the main floating action button
        mFABMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRatings)
                .attachTo(mFAB)
                .build();
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
            Toast.makeText(this, "Anda telah meng-Klik " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.navigate) {
            startActivity(new Intent(this, ActivityTouchEvent.class));
        }

        if (id == R.id.tabWithLibrary) {
            startActivity(new Intent(this, ActivityUsingTabLibrary.class));
        }

        if (id == R.id.vectorTestActivity) {
            startActivity(new Intent(this, ActivityVectorDrawable.class));
        }

        if (id == R.id.action_recycler_item_animations) {
            startActivity(new Intent(this, ActivityRecyclerItemAnimations.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public void onClick(View view) {
        //call instantiate item since getItem may return null depending on whether the PagerAdapter
        // is of type FragmentPagerAdapter or FragmentStatePagerAdapter

        //Fragment fragment = (Fragment) mAdapter.instantiateItem(mPager, mPager.getCurrentItem());
        Fragment mFragment = (Fragment) mAdapter.instantiateItem(mPager, mPager.getCurrentItem());

        if (mFragment != null) {

            if (mFragment instanceof SortListener) {

                if (view.getTag().equals(TAG_SORT_NAME)) {
                    //L.t(this,"Submenu SortName Clicked");
                    ((SortListener) mFragment).onSortByName();
                }

                if (view.getTag().equals(TAG_SORT_DATE)) {
                    //L.t(this,"Submenu SortDate Clicked");
                    ((SortListener) mFragment).onSortByDate();
                }

                if (view.getTag().equals(TAG_SORT_RATINGS)) {
                    //L.t(this,"Submenu SortRatings Clicked");
                    ((SortListener) mFragment).onSortByRating();
                }
            }
        } else {
            L.t(this, "Error Fragment Null");
        }


    }

    /*
    class MyPagerAdapter extends FragmentPagerAdapter{

        int icons[] = {R.drawable.ic_notifications_white_36dp, R.drawable.ic_map_white_36dp, R.drawable.ic_directions_white_36dp};
        String[] tabText = getResources().getStringArray(R.array.com.qferiz.qferiztrafficinfo.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabText = getResources().getStringArray(R.array.com.qferiz.qferiztrafficinfo.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            //MyFragment myFragment = MyFragment.getInstance(position);
            //return myFragment;
            return com.qferiz.qferiztrafficinfo.fragments.MyFragment.getInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return super.getPageTitle(position);
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {
            return 3; // JUMLAH TAB
        }
    }
    */

    private void toggleTranslateFAB(float slideOffset) {
        if (mFABMenu != null) {
            if (mFABMenu.isOpen()) {
                mFABMenu.close(true);
            }
            mFAB.setTranslationX(slideOffset * 200);
        }
    }

    public void onDrawerSlide(float slideOffset) {
        toggleTranslateFAB(slideOffset);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        int icons[] = {R.drawable.ic_notifications_white_36dp,
                R.drawable.ic_map_white_36dp,
                R.drawable.ic_directions_white_36dp};

        FragmentManager fragmentManager;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        @Override
        public Fragment getItem(int num) {
            //return MyFragment.getInstance(num);
            Fragment fragment = null;

            switch (num) {
                case TAB_SEARCH_RESULT:
                    fragment = FragmentSearch.newInstance("", "");
                    break;
                case TAB_HITS:
                    fragment = FragmentBoxOffice.newInstance("", "");
                    break;
                case TAB_UPCOMING:
                    fragment = FragmentUpcoming.newInstance("", "");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT; // Jumlah TAB
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

    /*
    public static class MyFragment extends Fragment {
        private TextView textView;

        public static MyFragment getInstance(int position){
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //return super.onCreateView(inflater, container, savedInstanceState);
            View layout = inflater.inflate(R.layout.fragment_my, container, false);
            textView = (TextView) layout.findViewById(R.id.txtPosition);
            Bundle bundle = getArguments();

            if (bundle != null){
                textView.setText("Halaman yang dipilih : "+bundle.getInt("position"));
            }

            return layout;
        }
    }
    */
}
