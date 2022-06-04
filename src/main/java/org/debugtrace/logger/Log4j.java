// Log4j.java
// (C) 2015 Masato Kokubo

package org.debugtrace.logger;

import java.util.HashMap;
import java.util.Map;

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
    // Level Map
    private static final Map<String, Level> levelMap = new HashMap<>();
    static {
        levelMap.put("default", Level.TRACE);
        levelMap.put("trace"  , Level.TRACE);
        levelMap.put("debug"  , Level.DEBUG);
        levelMap.put("info"   , Level.INFO );
        levelMap.put("warn"   , Level.WARN );
        levelMap.put("error"  , Level.ERROR);
        levelMap.put("fatal"  , Level.FATAL);
    }

    // Logger
    private org.apache.log4j.Logger logger;

    // Level
    private Level level = Level.TRACE;

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
    public void setLevel(String levelStr) {
        Level level = levelMap.get(levelStr);
        if (level != null)
            this.level = level;
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
