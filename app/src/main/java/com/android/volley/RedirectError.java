package com.android.volley;

/**
 * Indicates that there was a redirection.
 * Created by shhwang on 15. 10. 2.
 */
public class RedirectError extends VolleyError {
    public RedirectError() {}

    public RedirectError(final Throwable cause) {
        super(cause);
    }

    public RedirectError(final NetworkResponse response) {
        super(response);
    }
}
