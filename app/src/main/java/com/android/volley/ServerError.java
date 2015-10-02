package com.android.volley;

/**
 * Indicates that the server responded with an error response.
 * Created by shhwang on 15. 10. 2.
 */
public class ServerError extends VolleyError {
    public ServerError() {
        super();
    }
    public ServerError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}
