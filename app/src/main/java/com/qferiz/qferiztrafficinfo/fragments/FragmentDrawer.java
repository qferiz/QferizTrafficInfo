package com.qferiz.qferiztrafficinfo.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.qferiz.qferiztrafficinfo.R;
import com.qferiz.qferiztrafficinfo.activities.ActivityMain;
import com.qferiz.qferiztrafficinfo.adapters.AdapterDrawer;
import com.qferiz.qferiztrafficinfo.extras.Information;
import com.qferiz.qferiztrafficinfo.extras.MyApplication;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDrawer extends Fragment {
    /*
    STEPS TO HANDLE THE RECYCLER CLICK

    1 Create a class that EXTENDS RecylcerView.OnItemTouchListener

    2 Create an interface inside that class that supports click and long click and indicates the View that was clicked and the position where it was clicked

    3 Create a GestureDetector to detect ACTION_UP single tap and Long Press events

    4 Return true from the singleTap to indicate your GestureDetector has consumed the event.

    5 Find the childView containing the coordinates specified by the MotionEvent and if the childView is not null and the listener is not null either, fire a long click event

    6 Use the onInterceptTouchEvent of your RecyclerView to check if the childView is not null, the listener is not null and the gesture detector consumed the touch event

    7 if above condition holds true, fire the click event

    8 return false from the onInterceptedTouchEvent to give a chance to the childViews of the RecyclerView to process touch events if any.

    9 Add the onItemTouchListener object for our RecyclerView that uses our class created in step 1
     */

    //    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private RecyclerView mRecyclerDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private AdapterDrawer mAdapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View mContainer;
    private boolean mDrawerOpened = false;

    public FragmentDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(MyApplication.readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
//        if (savedInstanceState != null) {
//            mFromSavedInstanceState = true;
//        }
        mFromSavedInstanceState = savedInstanceState != null ? true : false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mRecyclerDrawer = (RecyclerView) layout.findViewById(R.id.drawerList);
        mAdapter = new AdapterDrawer(getActivity(), getData());
        //mAdapter.setClickListener(this);
        mRecyclerDrawer.setAdapter(mAdapter);
        mRecyclerDrawer.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerDrawer.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerDrawer, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getActivity(), "onClick "+position,Toast.LENGTH_SHORT).show();
                Log.d("QFERIZ", "Item Drawer Clicked, Position: " + position);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                ((ActivityMain) getActivity()).onDrawerItemClicked(position - 1);
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(getActivity(), "onLongClick "+position,Toast.LENGTH_SHORT).show();
            }
        }));

        return layout;
    }

    /** Untuk membuat List Item pada Navigation Drawer menggunakan ArrayList & Recylerview */
    /**
     * Load Static Data Inside a Drawer
     */
    public List<Information> getData() {
        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_notifications_grey600_24dp, R.drawable.ic_map_grey600_24dp, R.drawable.ic_directions_grey600_24dp};
        //String[] titles = {"Kirim Informasi", "Request Informasi", "Pengaturan", "Tentang"};
        String[] titles = getResources().getStringArray(R.array.drawer_tabs);

        for (int i = 0; i < titles.length; i++) {
            Information information = new Information();
            information.iconId = icons[i];
            information.title = titles[i];
            data.add(information);
        }

//        for(int i=0;i<20;i++){
//            Information current = new Information();
//            current.iconId = icons[i%icons.length];
//            current.title = titles[i%titles.length];
//            data.add(current);
//        }

        return data;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {

        mContainer = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    MyApplication.saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }


            /** Untuk membuat Status Bar/toolbar berwarna hitam tranparant */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                ((ActivityMain) getActivity()).onDrawerSlide(slideOffset);
                //Log.d("QFERIZ","offset "+ slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);

//                if (slideOffset < 0.6) {
//                    toolbar.setAlpha(1 - slideOffset);
//                }
            }
        };

        //Icon Navigation Drawer Animate
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
                if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
                    mDrawerLayout.openDrawer(mContainer);
                }
            }
        });
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
//            Log.d("QFERIZ", "Constructor Invoked");
            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("QFERIZ", "onSingleTapUp " + e);
                    //return super.onSingleTapUp(e);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                    Log.d("QFERIZ", "onLongPress " + e);
                    //super.onLongPress(e);
                }
            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }

            //Log.d("QFERIZ", "onInterceptTouchEvent "+gestureDetector.onTouchEvent(e)+" "+e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//            Log.d("QFERIZ", "onTouchEvent " + e);

        }
    }


//    implements QferizAdapter.ClickListener
//    @Override
//    public void itemClicked(View view, int position) {
//        startActivity(new Intent(getActivity(), SubActivity.class));
//    }
}
