package org.thinav.filemonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by shhwang on 15. 10. 2.
 */
public class fileMoniterActivity extends Activity {

    private EditText mEditText;

    private Button mToggleButton;
    private boolean mIsRunning;
    private TextView mToggleText;
    private String mWatchDir = "/storage/emulated/0/DCIM";
    private String mSaveFile = "monitor_log.csv";


    protected static String mExtraWatch = "WATCH_DIR";
    protected static String mExtraExtern = "SAVE_EXT";


    private boolean mExternalStorageAvailable = false;
    private boolean mExternalStorageWriteable = false;
    String mState = Environment.getExternalStorageState();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.watch_dir);
        mEditText.setText(mWatchDir);

        String loc = "";
        if (Environment.MEDIA_MOUNTED.equals(mState)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(mState)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        if (mExternalStorageAvailable && mExternalStorageWriteable) {
            File f = Environment.getExternalStorageDirectory();
            loc = f.getAbsolutePath() + "/" + mSaveFile;
        } else {
            loc = getFilesDir().toString() + "/" + mSaveFile;
        }

        TextView savedLoc = (TextView) findViewById(R.id.file_loc);
        savedLoc.setText(loc);

        mToggleText = (TextView) findViewById(R.id.status);
        mToggleButton = (Button) findViewById(R.id.toggle);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWatchDir = mEditText.getText().toString();
                File check = new File(mWatchDir);
                if (check.isDirectory()) {
                    toggleService();
                } else {
                    Toast.makeText(getApplicationContext(), mWatchDir + getText(R.string.toast_bad_dir), Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private int toggleService() {
        int retVal = 0;

        if (!mIsRunning) {
            Intent intent = new Intent(this, FileMonitorService.class);
            if (mExternalStorageAvailable && mExternalStorageWriteable) {
                intent.putExtra(mExtraExtern, true);
            } else {
                intent.putExtra(mExtraExtern, false);
            }
            intent.putExtra(mExtraWatch, mWatchDir);
            startService(intent);
            mIsRunning = true;

            mToggleButton.setText(R.string.stop);
            mToggleText.setText(R.string.running);
        } else {
            Intent intent = new Intent(this, FileMonitorService.class);
            stopService(intent);
            mIsRunning = false;

            mToggleButton.setText(R.string.start);
            mToggleText.setText(R.string.not_running);
        }
        return retVal;
    }
}
