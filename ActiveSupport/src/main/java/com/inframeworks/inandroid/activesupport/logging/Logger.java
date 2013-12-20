package com.inframeworks.inandroid.activesupport.logging;

import android.util.Log;

import com.inframeworks.inandroid.activesupport.Application;
import com.inframeworks.inandroid.activesupport.annotations.Beta;

/**
 * API for sending log output.
 * <p/>
 * The order in terms of verbosity, from least to most is error, warn, info, debug, verbose. Verbose should never be compiled into an application except during development. Debug logs are compiled in but stripped at runtime. Error, warning and info logs are always kept.
 *
 * @author rodrigoscna
 */
@Beta
public class Logger {
  /**
   * Checks to see whether or not the Logger is enabled in application's metadata. If nothing is
   * defined, the value false is assumed.
   *
   * @return Whether or not that the Logger is enabled.
   */
  private static Boolean isEnabled() {
    return Application.getApplicationMetaData().getBoolean("loggerEnabled", false);
  }

  /**
   * Returns the identification of the source of a log message.
   *
   * @return Identification of the source of the log message.
   */
  private static String getLoggerTag() {
    String loggerTag = Application.getApplicationMetaData().getString("loggerTag");
    if (loggerTag == null) {
      loggerTag = Application.APPLICATION_NAME;
    }

    return loggerTag;
  }

  /**
   * Manually enables the Logger.
   */
  public static void enable() {
  }

  /**
   * Manually disables the Logger.
   */
  public static void disable() {
  }

  /**
   * Send a Debug log message.
   *
   * @param message The message you would like logged.
   */
  public static void debug(String message) {
    debug(getLoggerTag(), message);
  }

  /**
   * Send a Debug log message.
   *
   * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
   * @param message The message you would like logged.
   */
  public static void debug(String tag, String message) {
    debug(tag, message, null);
  }

  /**
   * Send a Debug log message and log the exception.
   *
   * @param message   The message you would like logged.
   * @param throwable An exception to log.
   */
  public static void debug(String message, Throwable throwable) {
    debug(getLoggerTag(), message, throwable);
  }

  /**
   * Send a Debug log message and log the exception.
   *
   * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
   * @param message   The message you would like logged.
   * @param throwable An exception to log.
   */
  public static void debug(String tag, String message, Throwable throwable) {
    if (isEnabled()) {
      Log.d(tag, message, throwable);
    }
  }

  /**
   * Send an Error log message.
   *
   * @param message The message you would like logged.
   */
  public static void error(String message) {
    error(getLoggerTag(), message);
  }

  /**
   * Send an Error log message.
   *
   * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
   * @param message The message you would like logged.
   */
  public static void error(String tag, String message) {
    error(tag, message, null);
  }

  /**
   * Send an Error log message and log the exception.
   *
   * @param message   The message you would like logged.
   * @param throwable An exception to log.
   */
  public static void error(String message, Throwable throwable) {
    error(getLoggerTag(), message, throwable);
  }

  /**
   * Send an Error log message and log the exception.
   *
   * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
   * @param message   The message you would like logged.
   * @param throwable An exception to log.
   */
  public static void error(String tag, String message, Throwable throwable) {
    if (isEnabled()) {
      Log.e(tag, message, throwable);
    }
  }

  /**
   * Send an Info log message.
   *
   * @param message The message you would like logged.
   */
  public static void info(String message) {
    info(getLoggerTag(), message);
  }

  /**
   * Send an Info log message.
   *
   * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
   * @param message The message you would like logged.
   */
  public static void info(String tag, String message) {
    info(tag, message, null);
  }

  /**
   * Send an Info log message and log the exception.
   *
   * @param message   The message you would like logged.
   * @param throwable An exception to log.
   */
  public static void info(String message, Throwable throwable) {
    info(getLoggerTag(), message, throwable);
  }

  /**
   * Send an Info log message and log the exception.
   *
   * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
   * @param message   The message you would like logged.
   * @param throwable An exception to log.
   */
  public static void info(String tag, String message, Throwable throwable) {
    if (isEnabled()) {
      Log.i(tag, message, throwable);
    }
  }

  /**
   * Send a Verbose log message.
   *
   * @param message The message you would like logged.
   */
  public static void verbose(String message) {
    verbose(getLoggerTag(), message);
  }

  /**
   * Send a Verbose log message.
   *
   * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
   * @param message The message you would like logged.
   */
  public static void verbose(String tag, String message) {
    verbose(tag, message, null);
  }

  /**
   * Send a Verbose log message and log the exception.
   *
   * @param message   The message you would like logged.
   * @param throwable An exception to log.
   */
  public static void verbose(String message, Throwable throwable) {
    verbose(getLoggerTag(), message, throwable);
  }

  /**
   * Send a Verbose log message and log the exception.
   *
   * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
   * @param message   The message you would like logged.
   * @param throwable An exception to log.
   */
  public static void verbose(String tag, String message, Throwable throwable) {
    if (isEnabled()) {
      Log.v(tag, message, throwable);
    }
  }

  /**
   * Send a Warn log message.
   *
   * @param message The message you would like logged.
   */
  public static void warn(String message) {
    warn(getLoggerTag(), message);
  }

  /**
   * Send a Warn log message.
   *
   * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
   * @param message The message you would like logged.
   */
  public static void warn(String tag, String message) {
    warn(tag, message, null);
  }

  /**
   * Send a Warn log message and log the exception.
   *
   * @param message   The message you would like logged.
   * @param throwable An exception to log.
   */
  public static void warn(String message, Throwable throwable) {
    warn(getLoggerTag(), message, throwable);
  }

  /**
   * Send a Warn log message and log the exception.
   *
   * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
   * @param message   The message you would like logged.
   * @param throwable An exception to log.
   */
  public static void warn(String tag, String message, Throwable throwable) {
    if (isEnabled()) {
      Log.w(tag, message, throwable);
    }
  }
}
