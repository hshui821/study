package com.android.volley;

/**
 * Created by shhwang on 15. 9. 23.
 */
public interface Network {
    /**
     * Performs the specified request
     * @param request Request to process
     * @return A {@link NetworkResponse} with data and caching metadata; will never be null
     * @throws VolleyError on error
     */
    public NetworkReponse performRequest(Request<?> request) throws VolleyError;
}
