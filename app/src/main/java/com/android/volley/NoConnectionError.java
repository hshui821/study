package com.android.volley;

/**
 * Error indicating that no connection could be established when performing a Volley request.
 * Created by shhwang on 15. 10. 2.
 */
public class NoConnectionError extends NetworkError {
    public NoConnectionError() {
        super();
    }
    public NoConnectionError(Throwable reason) {
        super(reason);
    }
}
