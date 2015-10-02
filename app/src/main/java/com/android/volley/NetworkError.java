package com.android.volley;

/**
 * Indicates that there was a network error when performing a Volley request.
 * Created by shhwang on 15. 10. 2.
 */
public class NetworkError extends VolleyError{
    public NetworkError() {
        super();
    }
    public NetworkError(Throwable cause) {
        super(cause);
    }
    public NetworkError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

}
