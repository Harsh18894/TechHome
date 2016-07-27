package com.techHome.global;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.techHome.dto.OrderDTO;

import java.util.List;

/**
 * Created by Harsh on 7/23/2016.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    public static final String JSON_OBJECT_REQUEST = "json_obj_req";

    private com.android.volley.RequestQueue mRequestQueue;


    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
   /* synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }*/
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public com.android.volley.RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    private List<OrderDTO> orderDTOs;


    public List<OrderDTO> getOrderDTOs() {
        return orderDTOs;
    }

    public void setOrderDTOs(List<OrderDTO> orderDTOs) {
        this.orderDTOs = orderDTOs;
    }


}

