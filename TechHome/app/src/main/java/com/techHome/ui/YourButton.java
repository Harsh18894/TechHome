package com.techHome.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by USER on 02/04/2016.
 */
public class YourButton extends Button {
    Context context;
    public YourButton(Context context,AttributeSet attributes,int defStyle) {
        super(context,attributes,defStyle);
        this.context=context;
        init();
    }
    public YourButton(Context context,AttributeSet attributes) {
        super(context,attributes);
        this.context=context;
        init();
    }
    public YourButton(Context context) {
        super(context);
        this.context=context;
        init();
    }

    private void init() {
        Typeface tf=Typeface.createFromAsset(context.getAssets(),"fonts/Segoe_UI.ttf");
        setTypeface(tf);
    }
}
