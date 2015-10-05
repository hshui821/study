package com.android.volley.toolbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.Network;
import com.android.volley.RequestQueue;

import java.io.File;

/**
 * Created by shhwang on 15. 10. 5.
 */
public class Volley {
    private static final String DEFAULT_CACHE_DIR = "volley";
    public static RequestQueue newRequestQueue(Context context, HttpStack stack, int maxDiskCachBytes) {
        File CacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
        String userAgent ="volley/0";

        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        if (stack == null) {
            if (Build.VERSION.SDK_INT >=9 ) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack((AndroidHttpClient.newInstance(userAgent)));
            }

            Network network = new BasicNetwork(stack);
        }
    }
}
