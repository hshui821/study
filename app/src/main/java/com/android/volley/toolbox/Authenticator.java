package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;

/**
 * Created by shhwang on 15. 10. 11.
 */
public interface Authenticator {

    /**
     * Synchronously retrieves an auth token.
     *
     * @throws AuthFailureError If authentication did not succeed
     */
    public String getAuthToken() throws AuthFailureError;

    /**
     * Invalidates the provided auth token;
     */
    public void invalidateAuthToken(String authToken);
}
