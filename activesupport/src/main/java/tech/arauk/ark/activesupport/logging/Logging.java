package tech.arauk.ark.activesupport.logging;

import tech.arauk.ark.activesupport.annotations.Beta;

/**
 * An interface used to expose Logging methods as a standardized API.
 * <p>
 * If a new Logger is to be created, it should implement this interface and then
 * be set as the default logger through the
 * {@link tech.arauk.ark.activesupport.logging.Logger#setLogger(Logging)}
 * wrapper.
 *
 * @author Rodrigo Scomazzon do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public interface Logging {
    void debug(String tag, String message, Throwable throwable);

    void error(String tag, String message, Throwable throwable);

    void info(String tag, String message, Throwable throwable);

    void verbose(String tag, String message, Throwable throwable);

    void warn(String tag, String message, Throwable throwable);
}
