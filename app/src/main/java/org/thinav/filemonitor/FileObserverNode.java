package org.thinav.filemonitor;

import android.os.FileObserver;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by shhwang on 15. 10. 1.
 */
public class FileObserverNode {

    private final String mWatchPath;
    private final List<String[]> mLogCopy;
    private final FileObserver mPathObserver;

    private static String mAccess = "IN_ACCESS";
    private static String mModify =  "IN_MODIFY";
    private static String mAttrib = "IN_ATTRIB";
    private static String mCloseWrite = "IN_CLOSE_WRITE";
    private static String mCloseNoWrite = "IN_CLOSE_NOWRITE";
    private static String mOpen = "IN_OPEN";
    private static String mMoveFrom = "IN_MOVE_FROM";
    private static String mMoveTo = "IN_MOVE_TO";
    private static String mCreate = "IN_CREATE";
    private static String mDelete = "IN_DELETE";
    private static String mDeleteSelf = "IN_DELETE_SELF";
    private static String mMoveSelf="IN_MOVE_SELF";


    public FileObserverNode(String path, int observerMask, List<String[]> log) {

        mWatchPath = path;
        mLogCopy = log;
        mPathObserver = new FileObserver(path, observerMask) {
            @Override
            public void onEvent(int event, String path) {
                String eventName = "";

                switch (event) {
                    case FileObserver.MOVE_SELF:
                        eventName = mMoveSelf;
                        break;
                    case FileObserver.DELETE_SELF:
                        eventName = mDeleteSelf;
                        break;
                    case FileObserver.DELETE:
                        eventName = mDelete;
                        break;
                    case FileObserver.CREATE:
                        eventName = mCreate;
                        break;
                    case FileObserver.MOVED_TO:
                        eventName = mMoveTo;
                        break;
                    case FileObserver.MOVED_FROM:
                        eventName = mMoveFrom;
                        break;
                    case FileObserver.OPEN:
                        eventName = mOpen;
                        break;
                    case FileObserver.CLOSE_NOWRITE:
                        eventName = mCloseNoWrite;
                        break;
                    case FileObserver.CLOSE_WRITE:
                        eventName = mCloseWrite;
                        break;
                    case FileObserver.ATTRIB:
                        eventName = mAttrib;
                        break;
                    case FileObserver.MODIFY:
                        eventName = mModify;
                        break;
                    case FileObserver.ACCESS:
                        eventName = mAccess;
                        break;
                }

                if ((path != null) && (path.length() > 0) && (!(eventName.length() == 0))) {
                    String fullPath = mWatchPath + "/" + path;
                    File f = new File(fullPath);
                    long size = f.length();

                    long time = System.currentTimeMillis();
                    BigDecimal bdTime = new BigDecimal(time);
                    BigDecimal thousand = new BigDecimal(1000);
                    BigDecimal ftime = bdTime.divide(thousand);

                    String[] entry = {fullPath, String.valueOf(size), eventName, ftime.toString()};
                    mLogCopy.add(entry);
                }
            }
        };
    }
    public void startMyObserver() {
        mPathObserver.startWatching();;
    }
    public void stopMyObserver() {
        mPathObserver.stopWatching();;
    }
}
