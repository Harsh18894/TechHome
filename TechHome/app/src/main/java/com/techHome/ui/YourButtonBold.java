package com.techHome.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by USER on 02/04/2016.
 */
public class YourButtonBold extends TextView {
    Context context;
    public YourButtonBold(Context context,AttributeSet attributes,int defStyle) {
        super(context,attributes,defStyle);
        this.context=context;
        init();
    }
    public YourButtonBold(Context context,AttributeSet attributes) {
        super(context,attributes);
        this.context=context;
        init();
    }
    public YourButtonBold(Context context) {
        super(context);
        this.context=context;
        init();
    }

    private void init() {
        Typeface tf=Typeface.createFromAsset(context.getAssets(),"fonts/ufonts.com_segoe-ui-bold.ttf");
        setTypeface(tf);
    }


}
