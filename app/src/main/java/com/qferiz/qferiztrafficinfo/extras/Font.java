package com.qferiz.qferiztrafficinfo.extras;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Qferiz on 22/03/2015.
 */

// Class untuk mengatur Custom Fonts
public class Font {
    public static final Font  ROBOTO_MEDIUM = new Font("roboto-medium.ttf");
    public static final Font  ROBOTO_REGULER = new Font("roboto-regular.ttf");
    private final String assetName;
    private volatile Typeface typeface;

    public Font(String assetName) {
        this.assetName = assetName;
    }

    public void apply(Context context, TextView textView) {
        if (typeface == null) {
            synchronized (this) {
                if (typeface == null) {
                    typeface = Typeface.createFromAsset(context.getAssets(), assetName);
                }
            }
        }
        textView.setTypeface(typeface);
    }
}
