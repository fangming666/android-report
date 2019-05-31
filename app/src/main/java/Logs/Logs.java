package Logs;

import android.util.Log;

public class Logs {
    boolean isStartLog = true;

    public void logD(String tag, String msg) {
        if (isStartLog) {
            Log.d(tag, msg);
        }
    }
}
