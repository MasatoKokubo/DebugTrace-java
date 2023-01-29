// SLF4J.java
// (C) 2015 Masato Kokubo

package org.debugtrace.logger;

import java.util.function.Consumer;

import org.debugtrace.DebugTrace;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

/**
 * A logger using Log4J2.
 * 
 * @since 1.1.0
 * @author Masato Kokubo
 */
public class SLF4J implements Logger {
    // Logger
    private org.slf4j.Logger logger;

    // Level
    private static final Level level = Level.TRACE;

    // Log Consumer
    private Consumer<String> logConsumer;
    
    /**
     * Construct a SLF4J.
     */
    public SLF4J() {
        logger = LoggerFactory.getLogger(DebugTrace.class);
        logConsumer = logger::trace;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return logger.isEnabledForLevel(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        logConsumer.accept(message);
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
