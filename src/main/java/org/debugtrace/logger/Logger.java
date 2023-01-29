// Logger.java
// (C) 2015 Masato Kokubo

package org.debugtrace.logger;

/**
 * Interface of Logger classes.
 *
 * @since 1.0.0
 * @author Masato Kokubo
 */
@FunctionalInterface
public interface Logger {
    /**
     * Returns whether logging is enabled.
     *
     * @return true if logging is enabled; false otherwise
     */
    default boolean isEnabled() {
        return true;
    }

    /**
     * Output the message to the log.
     *
     * @param message a log message
     */
    void log(String message);
}
