package com.android.volley.com.android.volley;

import com.android.volley.*;
import com.android.volley.VolleyError;

/**
 * Retry policy for a request.
 * Created by shhwang on 15. 9. 26.
 */
public interface RetryPolicy {

    /**
     * Returns the current timeout (used for logging).
     */
    public int getCurrentTimeout();

    /**
     * Returns the current retry count (used for logging);
     */
    public int getCurrentRetryCount();

    /**
     * Prepares for the next retry by applying a backoff to the timeout.
     * @param error The error code of the last attempt.
     * @throws com.android.volley.com.android.volley.VolleyError In the event that the retry could not be performed (for example if we
     * ran out of attempts), the passed in error is thrown.
     */
    public void retry(com.android.volley.com.android.volley.VolleyError error) throws com.android.volley.com.android.volley.VolleyError;

}
