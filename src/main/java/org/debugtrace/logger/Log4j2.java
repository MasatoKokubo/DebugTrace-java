// Log4j2.java
// (C) 2015 Masato Kokubo

package org.debugtrace.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.debugtrace.DebugTrace;

/**
 * A logger using Log4J2.
 *
 * @since 1.0.0
 * @author Masato Kokubo
 */
public class Log4j2 implements Logger {
    // Logger
    private org.apache.logging.log4j.Logger logger;

    // Level
    private static final Level level = Level.TRACE;

    /**
     * Construct a Log4j2.
     */
    public Log4j2() {
        logger = LogManager.getLogger(DebugTrace.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return logger.isEnabled(level);
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
