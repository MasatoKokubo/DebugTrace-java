// Jdk.java
// (C) 2015 Masato Kokubo

package org.debugtrace.logger;

import java.util.logging.Level;

import org.debugtrace.DebugTrace;

/**
 * A logger using JDK Logger.
 *
 * @since 1.0.0
 * @author Masato Kokubo
*/
public class Jdk implements Logger {
    // Logger
    private java.util.logging.Logger logger;

    // Level
    private static final Level level = Level.FINEST;

    /**
     * Construct a Jdk.
     */
    public Jdk() {
        logger = java.util.logging.Logger.getLogger(DebugTrace.class.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return logger.isLoggable(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        logger.log(level, message);
    }

    /**
     * {@inheritDoc}
     * @since 3.4.0
     */
    @Override
    public String toString() {
        return getClass().getName() + " (level: " + level + ")";
    }
}
