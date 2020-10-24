// DebugTrace.java
// (C) 2015 Masato Kokubo

package org.debugtrace;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Have a trace state for a thread
 * 
 * @since 3.0.0
 * @author Masato Kokubo
 */
class State {
    private long threadId;
    private int nestLevel;
    private int previousNestLevel;
    private int previousLineCount;
    private final Deque<Long> times = new ArrayDeque<>();

    /**
     * Constructs a State.
     *
     * @param threadId a thread id
     */
    public State(long threadId) {
        this.threadId = threadId;
    }

    /**
     * Returns the thread id.
     *
     * @return the thread id
     */
    public long threadId() {
        return threadId;
    }


    /**
     * Returns the nest level.
     *
     * @return the nest level
     */
    public int nestLevel() {
        return nestLevel;
    }

    /**
     * Returns the previous nest level.
     *
     * @return the previous nest level
     */
    public int previousNestLevel() {
        return previousNestLevel;
    }

    /**
     * Returns the previous line count.
     *
     * @return the previous line count
     */
    public int previousLineCount() {
        return previousLineCount;
    }

    /**
     * Sets the previous line count.
     *
     * @param previousLineCount the previous line count to set
     */
    public void setPreviousLineCount(int previousLineCount) {
        this.previousLineCount = previousLineCount;
    }

    /**
     * Resets this instance.
     */
    public void reset() {
        nestLevel = 0;
        previousNestLevel = 0;
        previousLineCount = 0;
        times.clear();
    }

    /**
     * Returns a String representation of this object.
     *
     * @return a String representation of this object
     */
    @Override
    public String toString() {
        return "(State)["
            + "threadId: " + threadId
            + ", nestLevel: " + nestLevel
            + ", previousNestLevel: " + previousNestLevel
            + ", previousLineCount: " + previousLineCount
            + ", times: " + times
            + "]";
    }

    /**
     * Ups the nest level.
     */
    public void upNest() {
        previousNestLevel = nestLevel;
        if (nestLevel >= 0)
        // 3.0.3
        //  times.push(System.currentTimeMillis());
            times.push(System.nanoTime());
        ////
        ++nestLevel;
    }

    /**
     * Downs the nest level.
     *
     * @return the time when the corresponding upNest method was invoked
     */
    public long downNest() {
        previousNestLevel = nestLevel;
        --nestLevel;
    // 3.0.3
    //  return times.size() > 0 ? times.pop() : System.currentTimeMillis();
        return times.size() > 0 ? times.pop() : System.nanoTime();
    ////
    }
}
