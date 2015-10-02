package org.thinav.filemonitor;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shhwang on 15. 10. 1.
 */
public class FileMonitorService extends Service {

    // Service ID
    private int mNotifyId = 3141;
    private List<String[]> mLog;
    private List<FileObserverNode> mTreeNodes;
    private String mWatchDir;
    private boolean mSaveExt = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //methods to start and stop the observer objects in the list
    private void startWatchingTree() {
        for (FileObserverNode fon : mTreeNodes) {
            fon.startMyObserver();;
        }
    }

    private void stopWatchingTree() {
        for (FileObserverNode fon : mTreeNodes) {
            fon.stopMyObserver();;
        }
    }
    //Attach a new FileObserverNode
    private void attachNode (String path, int observerMask, List<String[]> logCopy) {
        // Is this the root directory we are supposed to watch?
        if (path.equalsIgnoreCase(mWatchDir)) {
            //Create a new observer
            FileObserverNode fon = new FileObserverNode(path, observerMask, logCopy);
            //Add it to the list
            mTreeNodes.add(fon);
        }
        // Open the directory
        File f = new File(path);
        File[] dirs = f.listFiles();
        if (dirs != null) {
            for (File child : dirs) {
                if (child.isDirectory()) {
                    //Check that it not the virtual directory (/proc) or the system directory, because
                    //attempting to watch these will create a race condition.
                    if ((!child.getAbsolutePath().equalsIgnoreCase("/proc")) && (!child.getAbsolutePath().equalsIgnoreCase("/sys"))
                            && (!child.getName().startsWith("."))) {
                        FileObserverNode fon = new FileObserverNode(child.getAbsolutePath(), observerMask, logCopy);
                        mTreeNodes.add(fon);
                        attachNode(child.getAbsolutePath(), observerMask, logCopy);
                    }
                }
            }
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Make this service a foreground service (take directly from SDK docs)
        Notification notification = new Notification(android.R.drawable.ic_menu_today, getText(android.R.string.notifification_short), System.currentTimeMillis();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        Intent notificationIntent = new Intent(this.FileMonitorActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, getText(android.R.string.notification_long), pendingIntent);
        startForeground(mNotifyId, notification);

        //Instantiate the log & the observer tree
        mLog = new ArrayList<String[]>();
        mTreeNodes = new ArrayList<FileObserverNode>();

        //Pluck the data from the intent
        Bundle b = intent.getExtras();
        mSaveExt = b != null ? b.getBoolean(FileMonitorActivity.mExtraExtern) : false;
        mWatchDir = b != null ? b.getString(FileMonitorActivity.mExterWatch) : "/";

        //Attach & activate monitors
        int observerFlags = FileObserver.ALL_EVENTS;
        attachNode(mWatchDir, observerFlags, mLog);
        startWatchingTree();

        Toast.makeText(this, getText(R.string.toast_server_started)+ String.valueOf(mTreeNodes.size()), Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopWathingTree();

        try {
            List<String[]> log = mLog;
            FileOutputStream fos;
            if (mSaveExt) {
                File save = new File(Environment.getExternalStorageDirectory(), FileMonitorActivity.mSaveFile);
                fos = new FileOutputStream(save);
            } else {
                fos = openFileOutput(FileMonitorActivity.mSaveFile, Context.MODE_WORLD_READABLE);
            }

            for (int i = 0; i < log.size(); i++) {
                String[] t = (String[]) log.get(i);
                String t2 = t[0] + "," + t[1] + "," + t[2] + "," + t[3] + "\n";
                fos.write(t2.getBytes());
            }
            fos.close();
        } catch (Exception e) {
            Toast.makeText(this, getText(R.string.toast_service_error) + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, getText(R.string.toast_service_stop) + String.valueOf(mLog.size()), Toast.LENGTH_SHORT).show();
        stopForeground(true);
        super.onDestroy();
    }

}
