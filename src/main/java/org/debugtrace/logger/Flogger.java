// Flogger.java
// (C) 2015 Masato Kokubo

package org.debugtrace.logger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.google.common.flogger.FluentLogger;

import org.debugtrace.DebugTrace;

/**
 * A logger using Flogger.
 *
 * @since 3.1.0
 * @author Masato Kokubo
 */
public class Flogger implements Logger {
    // Level Map
    private static final Map<String, Level> levelMap = new HashMap<>();
    static {
        levelMap.put("default", Level.FINEST );
        levelMap.put("finest" , Level.FINEST );
        levelMap.put("finer"  , Level.FINER  );
        levelMap.put("fine"   , Level.FINE   );
        levelMap.put("config" , Level.CONFIG );
        levelMap.put("info"   , Level.INFO   );
        levelMap.put("warning", Level.WARNING);
        levelMap.put("severe" , Level.SEVERE );
     }

    // Logger
    private FluentLogger logger;

    // Api
    private FluentLogger.Api loggerApi; // logger.at(Level.FINEST);

    /**
     * Construct a Flogger.
     */
    public Flogger() {
        logger =  DebugTrace.getFluentLogger();
        loggerApi = logger.at(Level.FINEST);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLevel(String levelStr) {
        Level level = levelMap.get(levelStr);
        if (level != null)
            loggerApi = logger.at(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return loggerApi.isEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        loggerApi.log(message);
    }
}
