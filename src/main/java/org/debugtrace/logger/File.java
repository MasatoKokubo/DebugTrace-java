// FileLogger.java
// (C) 2015 Masato Kokubo

package org.debugtrace.logger;

import java.io.FileWriter;
import java.io.IOException;

import org.debugtrace.DebugTrace;

/**
 * A logger that outputs directly to a file.
 *
 * @since 3.4.0
 * @author Masato Kokubo
 */
public class File implements Logger {
    private static final Object lock = new Object();
    private java.io.File file;
    private boolean writeErrorReported;
    private String lineSeparator = System.getProperty("line.separator");

    /**
     * Constructs a org.debugtrace.logger.File.
     * @param file the output destination java.io.File
     * @param append Append output to the existing log file if true, initialize and output otherwise
     */
// 3.5.1
//  public File(java.io.File file) {
    public File(java.io.File file, boolean append) {
////
        this.file = file;
        try {
        // 3.5.1
        //  new FileWriter(file, true).close();
            new FileWriter(file, append).close();
        ////
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        synchronized (lock) {
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(DebugTrace.appendTimestamp(message) + lineSeparator);
            } catch (IOException e) {
                if (!writeErrorReported) {
                    System.err.println(e.toString());
                    writeErrorReported = true;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getName() + " (file: " + file.getPath() + ")";
    }
}
