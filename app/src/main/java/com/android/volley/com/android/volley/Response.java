package com.android.volley.com.android.volley;

import com.android.volley.*;
import com.android.volley.Cache;

/**
 * Created by shhwang on 15. 9. 26.
 */
public class Response<T> {
    public interface Listener<T> {
        public void onResponse(T response);
    }

    public interface ErrorListener {
        public void onErrorResponse(VolleyError response);
    }

    public static <T> Response<T> success(T result, com.android.volley.com.android.volley.Cache.Entry cacheEntry) {
        return new Response<T>(result, cacheEntry);
    }

    public static <T> Response<T> error(VolleyError error) {
        return new Response<T>(error);
    }

    public final T result;

    public final com.android.volley.com.android.volley.Cache.Entry cacheEntry;

    public final VolleyError error;

    public boolean intermediate = false;

    public boolean isSuccess() {
        return error == null;
    }

    private Response(T result, com.android.volley.com.android.volley.Cache.Entry cacheEntry) {
        this.result = result;
        this.cacheEntry = cacheEntry;
        this.error = null;
    }

    private Response(VolleyError error) {
        this.result = null;
        this.cacheEntry = null;
        this.error = error;
    }

}
