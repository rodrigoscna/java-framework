package tech.arauk.ark.activesupport.logging;

import tech.arauk.ark.activesupport.annotations.Beta;

/**
 * API for sending log output.
 * <p/>
 * The order in terms of verbosity, from least to most is error, warn, info,
 * debug, verbose. Verbose should never be compiled into an application except
 * during development. Debug logs are compiled in but stripped at runtime.
 * Error, warning and info logs are always kept.
 *
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class Logger {
    private static final String LOGGER_TAG = "tech.arauk.ark.activesupport.logging";
    private static Logger INSTANCE_HOLDER;
    private Boolean mIsEnabled;
    private Logging mLogger;
    private String mLoggerTag;

    private Logger(String loggerTag) {
        mIsEnabled = true;
        mLoggerTag = loggerTag;
    }

    private Logger(Logging logger, String loggerTag) {
        mIsEnabled = true;
        mLogger = logger;
        mLoggerTag = loggerTag;
    }

    public static Logger getDefaultLogger() {
        if (INSTANCE_HOLDER == null) {
            INSTANCE_HOLDER = new Logger(LOGGER_TAG);
        }

        return INSTANCE_HOLDER;
    }

    public static Logger getDefaultLogger(Logging logging) {
        return getDefaultLogger(logging, LOGGER_TAG);
    }

    public static Logger getDefaultLogger(Logging logging, String loggerTag) {
        if (INSTANCE_HOLDER == null) {
            INSTANCE_HOLDER = new Logger(logging, loggerTag);
        }

        return INSTANCE_HOLDER;
    }

    /**
     * Sets a logging library for the application. The interface must implement
     * the {@link tech.arauk.ark.activesupport.logging.Logging Logging}
     * interface.
     *
     * @param logger A object which implements the
     *               {@link tech.arauk.ark.activesupport.logging.Logging Logging}
     *               interface.
     */
    public void setLogger(Logging logger) {
        mLogger = logger;
    }

    /**
     * Checks to see whether or not the Logger is enabled.
     *
     * @return Whether or not that the Logger is enabled.
     */
    public Boolean isEnabled() {
        return mIsEnabled;
    }

    /**
     * Returns the identification of the source of a log message.
     *
     * @return Identification of the source of the log message.
     */
    public String getLoggerTag() {
        return mLoggerTag;
    }

    /**
     * Sets the Logger tag for future log messages.
     */
    public void setLoggerTag(String loggerTag) {
        mLoggerTag = loggerTag;
    }

    /**
     * Manually enables the Logger.
     */
    public void enable() {
        mIsEnabled = true;
    }

    /**
     * Manually disables the Logger.
     */
    public void disable() {
        mIsEnabled = false;
    }

    /**
     * Send a Debug log message.
     *
     * @param message The message you would like logged.
     */
    public void debug(String message) {
        debug(getLoggerTag(), message);
    }

    /**
     * Send a Debug log message.
     *
     * @param tag     Used to identify the source of a log message. It usually
     *                identifies the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public void debug(String tag, String message) {
        debug(tag, message, null);
    }

    /**
     * Send a Debug log message and log the exception.
     *
     * @param message   The message you would like logged.
     * @param throwable An exception to log.
     */
    public void debug(String message, Throwable throwable) {
        debug(getLoggerTag(), message, throwable);
    }

    /**
     * Send a Debug log message and log the exception.
     *
     * @param tag       Used to identify the source of a log message. It usually
     *                  identifies the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log.
     */
    public void debug(String tag, String message, Throwable throwable) {
        if (isEnabled()) {
            mLogger.debug(tag, message, throwable);
        }
    }

    /**
     * Send an Error log message.
     *
     * @param message The message you would like logged.
     */
    public void error(String message) {
        error(getLoggerTag(), message);
    }

    /**
     * Send an Error log message.
     *
     * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public void error(String tag, String message) {
        error(tag, message, null);
    }

    /**
     * Send an Error log message and log the exception.
     *
     * @param message   The message you would like logged.
     * @param throwable An exception to log.
     */
    public void error(String message, Throwable throwable) {
        error(getLoggerTag(), message, throwable);
    }

    /**
     * Send an Error log message and log the exception.
     *
     * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log.
     */
    public void error(String tag, String message, Throwable throwable) {
        if (isEnabled()) {
            mLogger.error(tag, message, throwable);
        }
    }

    /**
     * Send an Info log message.
     *
     * @param message The message you would like logged.
     */
    public void info(String message) {
        info(getLoggerTag(), message);
    }

    /**
     * Send an Info log message.
     *
     * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public void info(String tag, String message) {
        info(tag, message, null);
    }

    /**
     * Send an Info log message and log the exception.
     *
     * @param message   The message you would like logged.
     * @param throwable An exception to log.
     */
    public void info(String message, Throwable throwable) {
        info(getLoggerTag(), message, throwable);
    }

    /**
     * Send an Info log message and log the exception.
     *
     * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log.
     */
    public void info(String tag, String message, Throwable throwable) {
        if (isEnabled()) {
            mLogger.info(tag, message, throwable);
        }
    }

    /**
     * Send a Verbose log message.
     *
     * @param message The message you would like logged.
     */
    public void verbose(String message) {
        verbose(getLoggerTag(), message);
    }

    /**
     * Send a Verbose log message.
     *
     * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public void verbose(String tag, String message) {
        verbose(tag, message, null);
    }

    /**
     * Send a Verbose log message and log the exception.
     *
     * @param message   The message you would like logged.
     * @param throwable An exception to log.
     */
    public void verbose(String message, Throwable throwable) {
        verbose(getLoggerTag(), message, throwable);
    }

    /**
     * Send a Verbose log message and log the exception.
     *
     * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log.
     */
    public void verbose(String tag, String message, Throwable throwable) {
        if (isEnabled()) {
            mLogger.verbose(tag, message, throwable);
        }
    }

    /**
     * Send a Warn log message.
     *
     * @param message The message you would like logged.
     */
    public void warn(String message) {
        warn(getLoggerTag(), message);
    }

    /**
     * Send a Warn log message.
     *
     * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public void warn(String tag, String message) {
        warn(tag, message, null);
    }

    /**
     * Send a Warn log message and log the exception.
     *
     * @param message   The message you would like logged.
     * @param throwable An exception to log.
     */
    public void warn(String message, Throwable throwable) {
        warn(getLoggerTag(), message, throwable);
    }

    /**
     * Send a Warn log message and log the exception.
     *
     * @param tag       Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log.
     */
    public void warn(String tag, String message, Throwable throwable) {
        if (isEnabled()) {
            mLogger.warn(tag, message, throwable);
        }
    }
}
