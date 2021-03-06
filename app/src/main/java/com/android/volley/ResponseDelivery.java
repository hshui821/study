package com.android.volley;

/**
 * Created by shhwang on 15. 9. 26.
 */
public interface ResponseDelivery {
    public void postResponse(Request<?> request, Response<?> response);
    public void postResponse(Request<?> request, Response<?> response, Runnable runnable);
    public void postError(Request<?> request, VolleyError error);
}
