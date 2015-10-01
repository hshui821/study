package com.android.volley.com.android.volley;

import android.os.Handler;

import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;

import java.util.concurrent.Executor;

/**
 * Delivers responses and errors.
 * Created by shhwang on 15. 9. 26.
 */
public class ExecutorDelivery implements com.android.volley.com.android.volley.ResponseDelivery {
    private final Executor mResponsePoster;

    public ExecutorDelivery(final Handler handler) {
        mResponsePoster = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    /**
     * Creates a new response deliver interface, mockable version
     * for testing.
     * @param executor For running deliver tasks
     */
    public ExecutorDelivery(Executor executor) {
        mResponsePoster = executor;
    }

    @Override
    public void postResponse(com.android.volley.com.android.volley.Request<?> request, com.android.volley.com.android.volley.Response<?> response) {
        postResponse(request, response, null);
    }

    @Override
    public void postResponse(com.android.volley.com.android.volley.Request<?> request, com.android.volley.com.android.volley.Response<?> response, Runnable runnable) {
        request.markDelivered();;
        request.addMarker("post-response");
        mResponsePoster.execute(new ResponseDeliveryRunnable(request, response, runnable));
    }

    @Override
    public void postError(com.android.volley.com.android.volley.Request<?> request, VolleyError error) {
        request.addMaker("post-error");
        com.android.volley.com.android.volley.Response<?> response = com.android.volley.com.android.volley.Response.error(error);
        mResponsePoster.execute(new ResponseDeliveryRunnable(request, response, null));
    }

    /**
     * A Runnable used for delivering network responses to a listener on teh
     * main thread.
     */
    @SuppressWarnings("rawtypes")
    private class ResponseDeliveryRunnable implements Runnable {
        private final com.android.volley.com.android.volley.Request mRequest;
        private final com.android.volley.com.android.volley.Response mResponse;
        private final Runnable mRunnable;

        public ResponseDeliveryRunnable(com.android.volley.com.android.volley.Request request, com.android.volley.com.android.volley.Response response, Runnable runnable) {
            mRequest = request;
            mResponse = response;
            mRunnable = runnable;
        }

        @Override
        public void run() {
            if (mRequest.isCanceled()) {
                mRequest.finish("canceled-at-delivery");
                return;
            }

            if (mResponse.isSuccess()) {
                mRequest.deliverResponse(mResponse.result);
            } else {
                mRequest.deliverError(mResponse.error);
            }

            if (mResponse.intermediate) {
                mRequest.addMarker("intermediate-response");
            } else {
                mRequest.finish("done");
            }

            if (mRunnable != null) {
                mRunnable.run();
            }
        }
    }
}
