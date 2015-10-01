package com.android.volley.com.android.volley;

import android.util.Log;

import com.android.volley.*;
import com.android.volley.NetworkResponse;

/**
 * Created by shhwang on 15. 9. 26.
 */
public class VolleyError extends Exception{
    public final com.android.volley.com.android.volley.NetworkResponse networkResponse;
    private long networkTimeMs;

    public VolleyError() {
        networkResponse = null;
    }

    public VolleyError(com.android.volley.com.android.volley.NetworkResponse response) {
        networkResponse = response;
    }
    
    public VolleyError(String exceptionMessage) {
        super(exceptionMessage);
        networkResponse = null;
    }
    
    public VolleyError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage. reason);
        networkResponse = null;
    }

    public VolleyError(Throwable cause) {
        super(cause);
        networkResponse = null;
    }

    /* package */ void setNetworkTimeMs(long networkTimeMs) {
        this.networkTimeMs = networkTimeMs;
    }

    public long getNetworkTimeMs() {
        return networkTimeMs;
    }

}
