package com.android.volley.com.android.volley;

import com.android.volley.*;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;

/**
 * Created by shhwang on 15. 9. 23.
 */
public interface Network {
    /**
     * Performs the specified request
     * @param request Request to process
     * @return A {@link com.android.volley.com.android.volley.NetworkResponse} with data and caching metadata; will never be null
     * @throws VolleyError on error
     */
    public NetworkReponse performRequest(com.android.volley.com.android.volley.Request<?> request) throws VolleyError;
}
