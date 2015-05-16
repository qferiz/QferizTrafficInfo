package com.qferiz.qferiztrafficinfo.views;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Qferiz on 26/03/2015.
 */
public class MyView extends TextView {
    Paint paint;
    public static final String TAG = "QFERIZ";


    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init();
    }

    public MyView(Context context, AttributeSet attributeSet, int defStyleAttr){
        super(context, attributeSet, defStyleAttr);
        init();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "MyView dispatchTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "MyView dispatchTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "MyView dispatchTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "MyView dispatchTouchEvent CANCEL");
                break;
        }

        boolean b = super.dispatchTouchEvent(event);
        Log.d(TAG, "MyView dispatchTouchEvent RETURN "+b);
        return b;
    }

    public boolean onTouchEvent(MotionEvent event){
        getParent().requestDisallowInterceptTouchEvent(true);

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "MyView onTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "MyView onTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "MyView onTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "MyView onTouchEvent CANCEL");
                break;

        }

        boolean b = super.onTouchEvent(event);
        //Log.d(TAG, "MyView onTouchEvent RETURN "+b);
        Log.d(TAG, "MyView onTouchEvent RETURN "+true);
        //return b;
        return true;
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        //setWillNotDraw(false);
    }
}
