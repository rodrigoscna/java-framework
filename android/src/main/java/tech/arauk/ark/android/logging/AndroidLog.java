package tech.arauk.ark.android.logging;

import android.util.Log;

import tech.arauk.ark.activesupport.logging.Logging;

/**
 * A wrapper class for the android Log library.
 *
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
public class AndroidLog implements Logging {
    @Override
    public void debug(String tag, String message, Throwable throwable) {
        Log.d(tag, message, throwable);
    }

    @Override
    public void error(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }

    @Override
    public void info(String tag, String message, Throwable throwable) {
        Log.i(tag, message, throwable);
    }

    @Override
    public void verbose(String tag, String message, Throwable throwable) {
        Log.v(tag, message, throwable);
    }

    @Override
    public void warn(String tag, String message, Throwable throwable) {
        Log.w(tag, message, throwable);
    }
}
