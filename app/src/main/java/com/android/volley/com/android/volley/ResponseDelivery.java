package com.android.volley.com.android.volley;

import com.android.volley.*;
import com.android.volley.Request;

/**
 * Created by shhwang on 15. 9. 26.
 */
public interface ResponseDelivery {
    public void postResponse(com.android.volley.Request<?> request, Response<?> response);
    public void postResponse(com.android.volley.Request<?> request, Response<?> response, Runnable runnable);
    public void postError(Request<?> request, VolleyError error);
}
