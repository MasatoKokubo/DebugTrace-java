// Log4j.java
// (C) 2015 Masato Kokubo

package org.debugtrace.logger;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.debugtrace.DebugTrace;

/**
 * A logger using Log4J.
 *
 * @since 1.0.0
 * @author Masato Kokubo
 */
public class Log4j implements Logger {
    // Logger
    private org.apache.log4j.Logger logger;

    // Level
    private static final Level level = Level.TRACE;

    /**
     * Construct a Log4j.
     */
    public Log4j() {
        logger = LogManager.getLogger(DebugTrace.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return logger.isEnabledFor(level);
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
