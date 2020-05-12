// Tuple.java
// (C) 2015 Masato Kokubo

package org.debugtrace.helper;

/**
 * Tuple interface.
 *
 * @since 3.0.0
 * @author Masato Kokubo
 */
public interface Tuple {
    /**
     * Tuple of 1 element. 
     *
     * @param <T1> the type of value 1 
     */
    public static class _1<T1> implements Tuple {
        /** the 1st value */
        private T1 value1;

        protected _1(T1 value1) {
            this.value1 = value1;
        }

        /**
         * Returns the 1st value.
         *
         * @return the 1st value
         */
        public T1 value1() {return value1;}
    }

    /**
     * Tuple of 2 elements 
     *
     * @param <T1> the type of value 1 
     * @param <T2> the type of value 2 
     */
    public static class _2<T1, T2> extends _1<T1> {
        /** the 2nd value */
        private T2 value2;

        protected _2(T1 value1, T2 value2) {
            super(value1);
            this.value2 = value2;
        }

        /**
         * Returns the 2nd value.
         *
         * @return the 2nd value
         */
        public T2 value2() {return value2;}
    }

    /**
     * Tuple of 3 elements
     *
     * @param <T1> the type of value 1 
     * @param <T2> the type of value 2 
     * @param <T3> the type of value 3 
     */
    public static class _3<T1, T2, T3> extends _2<T1, T2> {
        /** the 3rd value */
        private T3 value3;

        protected _3(T1 value1, T2 value2, T3 value3) {
            super(value1, value2);
            this.value3 = value3;
        }

        /**
         * Returns the 3rd value.
         *
         * @return the 3rd value
         */
        public T3 value3() {return value3;}
    }

    /**
     * Tuple of 4 elements 
     *
     * @param <T1> the type of value 1 
     * @param <T2> the type of value 2 
     * @param <T3> the type of value 3 
     * @param <T4> the type of value 3 
    */
    public static class _4<T1, T2, T3, T4> extends _3<T1, T2, T3> {
        /** the 4th value */
        private T4 value4;

        protected _4(T1 value1, T2 value2, T3 value3, T4 value4) {
            super(value1, value2, value3);
            this.value4 = value4;
        }

        /**
         * Returns the 4th value.
         *
         * @return the 4th value
         */
        public T4 value4() {return value4;}
    }

    /**
     * Returns a tuple containing 1 element.
     *
     * @param <T1> the type of value 1 
     * @param value1 the 1st value
     * @return Tuple._1
     */
    public static <T1> _1<T1> of(T1 value1) {
        return new _1<T1>(value1);
    }

    /**
     * Returns a tuple containing 2 elements.
     *
     * @param <T1> the type of value 1 
     * @param <T2> the type of value 2 
     * @param value1 the 1st value
     * @param value2 the 2nd value
     * @return Tuple._2
     */
    public static <T1, T2> _2<T1, T2> of(T1 value1, T2 value2) {
        return new _2<T1, T2>(value1, value2);
    }

    /**
     * Returns a tuple containing 3 elements.
     *
     * @param <T1> the type of value 1 
     * @param <T2> the type of value 2 
     * @param <T3> the type of value 3 
     * @param value1 the 1st value
     * @param value2 the 2nd value
     * @param value3 the 3rd value
     * @return Tuple._3
     */
    public static <T1, T2, T3> _3<T1, T2, T3> of(T1 value1, T2 value2, T3 value3) {
        return new _3<T1, T2, T3>(value1, value2, value3);
    }

    /**
     * Returns a tuple containing 4 elements.
     *
     * @param <T1> the type of value 1 
     * @param <T2> the type of value 2 
     * @param <T3> the type of value 3 
     * @param <T4> the type of value 3 
     * @param value1 the 1st value
     * @param value2 the 2nd value
     * @param value3 the 3rd value
     * @param value4 the 4th value
     * @return Tuple._4
    */
     public static <T1, T2, T3, T4> _4<T1, T2, T3, T4> of(T1 value1, T2 value2, T3 value3, T4 value4) {
        return new _4<T1, T2, T3, T4>(value1, value2, value3, value4);
    }
}
