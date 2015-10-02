package com.android.volley;

/**
 * Indicates that the server's response could not be parsed.
 * Created by shhwang on 15. 10. 2.
 */
public class ParseError extends VolleyError{
    public ParseError() { }

    public ParseError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public ParseError(Throwable cause) {
        super(cause);
    }
}
