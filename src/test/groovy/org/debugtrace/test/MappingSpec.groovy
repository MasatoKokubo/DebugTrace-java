// MappingTest.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test;

import java.sql.Types;
import java.util.Calendar;
import org.debugtrace.DebugTrace;
import spock.lang.*

/**
 * Mapping Test.
 *
 * @since 2.4.0
 * @author Masato Kokubo
 */
@Unroll
class MappingSpec extends Specification {
    def testCalendarSpec() {
        when: DebugTrace.print('Calendar', 'Calendar.ERA                 ', Calendar.ERA                 ); then: DebugTrace.lastLog.indexOf('(Calendar.ERA)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.YEAR                ', Calendar.YEAR                ); then: DebugTrace.lastLog.indexOf('(Calendar.YEAR)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.MONTH               ', Calendar.MONTH               ); then: DebugTrace.lastLog.indexOf('(Calendar.MONTH)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.WEEK_OF_YEAR        ', Calendar.WEEK_OF_YEAR        ); then: DebugTrace.lastLog.indexOf('(Calendar.WEEK_OF_YEAR)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.WEEK_OF_MONTH       ', Calendar.WEEK_OF_MONTH       ); then: DebugTrace.lastLog.indexOf('(Calendar.WEEK_OF_MONTH)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.DATE                ', Calendar.DATE                ); then: DebugTrace.lastLog.indexOf('(Calendar.DATE/DAY_OF_MONTH)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.DAY_OF_MONTH        ', Calendar.DAY_OF_MONTH        ); then: DebugTrace.lastLog.indexOf('(Calendar.DATE/DAY_OF_MONTH)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.DAY_OF_YEAR         ', Calendar.DAY_OF_YEAR         ); then: DebugTrace.lastLog.indexOf('(Calendar.DAY_OF_YEAR)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.DAY_OF_WEEK         ', Calendar.DAY_OF_WEEK         ); then: DebugTrace.lastLog.indexOf('(Calendar.DAY_OF_WEEK)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.DAY_OF_WEEK_IN_MONTH', Calendar.DAY_OF_WEEK_IN_MONTH); then: DebugTrace.lastLog.indexOf('(Calendar.DAY_OF_WEEK_IN_MONTH)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.AM_PM               ', Calendar.AM_PM               ); then: DebugTrace.lastLog.indexOf('(Calendar.AM_PM)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.HOUR                ', Calendar.HOUR                ); then: DebugTrace.lastLog.indexOf('(Calendar.HOUR)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.HOUR_OF_DAY         ', Calendar.HOUR_OF_DAY         ); then: DebugTrace.lastLog.indexOf('(Calendar.HOUR_OF_DAY)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.MINUTE              ', Calendar.MINUTE              ); then: DebugTrace.lastLog.indexOf('(Calendar.MINUTE)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.SECOND              ', Calendar.SECOND              ); then: DebugTrace.lastLog.indexOf('(Calendar.SECOND)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.MILLISECOND         ', Calendar.MILLISECOND         ); then: DebugTrace.lastLog.indexOf('(Calendar.MILLISECOND)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.ZONE_OFFSET         ', Calendar.ZONE_OFFSET         ); then: DebugTrace.lastLog.indexOf('(Calendar.ZONE_OFFSET)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.DST_OFFSET          ', Calendar.DST_OFFSET          ); then: DebugTrace.lastLog.indexOf('(Calendar.DST_OFFSET)') >= 0
        when: DebugTrace.print('Calendar', 'Calendar.FIELD_COUNT         ', Calendar.FIELD_COUNT         ); then: DebugTrace.lastLog.indexOf('(Calendar.FIELD_COUNT)') >= 0
    }

    def testWeekSpec() {
        when: DebugTrace.print('CalendarWeek', 'Calendar.SUNDAY   ', Calendar.SUNDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.SUNDAY)') >= 0
        when: DebugTrace.print('CalendarWeek', 'Calendar.MONDAY   ', Calendar.MONDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.MONDAY)') >= 0
        when: DebugTrace.print('CalendarWeek', 'Calendar.TUESDAY  ', Calendar.TUESDAY  ); then: DebugTrace.lastLog.indexOf('(Calendar.TUESDAY)') >= 0
        when: DebugTrace.print('CalendarWeek', 'Calendar.WEDNESDAY', Calendar.WEDNESDAY); then: DebugTrace.lastLog.indexOf('(Calendar.WEDNESDAY)') >= 0
        when: DebugTrace.print('CalendarWeek', 'Calendar.THURSDAY ', Calendar.THURSDAY ); then: DebugTrace.lastLog.indexOf('(Calendar.THURSDAY)') >= 0
        when: DebugTrace.print('CalendarWeek', 'Calendar.FRIDAY   ', Calendar.FRIDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.FRIDAY)') >= 0
        when: DebugTrace.print('CalendarWeek', 'Calendar.SATURDAY ', Calendar.SATURDAY ); then: DebugTrace.lastLog.indexOf('(Calendar.SATURDAY)') >= 0

        when: DebugTrace.print('week', Calendar.SUNDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.SUNDAY)') >= 0
        when: DebugTrace.print('week', Calendar.MONDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.MONDAY)') >= 0
        when: DebugTrace.print('week', Calendar.TUESDAY  ); then: DebugTrace.lastLog.indexOf('(Calendar.TUESDAY)') >= 0
        when: DebugTrace.print('week', Calendar.WEDNESDAY); then: DebugTrace.lastLog.indexOf('(Calendar.WEDNESDAY)') >= 0
        when: DebugTrace.print('week', Calendar.THURSDAY ); then: DebugTrace.lastLog.indexOf('(Calendar.THURSDAY)') >= 0
        when: DebugTrace.print('week', Calendar.FRIDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.FRIDAY)') >= 0
        when: DebugTrace.print('week', Calendar.SATURDAY ); then: DebugTrace.lastLog.indexOf('(Calendar.SATURDAY)') >= 0
    }

    def testMonthSpec() {
        when: DebugTrace.print('CalendarMonth', 'Calendar.JANUARY  ', Calendar.JANUARY  ); then: DebugTrace.lastLog.indexOf('(Calendar.JANUARY)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.FEBRUARY ', Calendar.FEBRUARY ); then: DebugTrace.lastLog.indexOf('(Calendar.FEBRUARY)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.MARCH    ', Calendar.MARCH    ); then: DebugTrace.lastLog.indexOf('(Calendar.MARCH)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.APRIL    ', Calendar.APRIL    ); then: DebugTrace.lastLog.indexOf('(Calendar.APRIL)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.MAY      ', Calendar.MAY      ); then: DebugTrace.lastLog.indexOf('(Calendar.MAY)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.JUNE     ', Calendar.JUNE     ); then: DebugTrace.lastLog.indexOf('(Calendar.JUNE)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.JULY     ', Calendar.JULY     ); then: DebugTrace.lastLog.indexOf('(Calendar.JULY)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.AUGUST   ', Calendar.AUGUST   ); then: DebugTrace.lastLog.indexOf('(Calendar.AUGUST)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.SEPTEMBER', Calendar.SEPTEMBER); then: DebugTrace.lastLog.indexOf('(Calendar.SEPTEMBER)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.OCTOBER  ', Calendar.OCTOBER  ); then: DebugTrace.lastLog.indexOf('(Calendar.OCTOBER)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.NOVEMBER ', Calendar.NOVEMBER ); then: DebugTrace.lastLog.indexOf('(Calendar.NOVEMBER)') >= 0
        when: DebugTrace.print('CalendarMonth', 'Calendar.DECEMBER ', Calendar.DECEMBER ); then: DebugTrace.lastLog.indexOf('(Calendar.DECEMBER)') >= 0
    }

    def testAmPmSpec() {
        when: DebugTrace.print('CalendarAmPm', 'Calendar.AM ', Calendar.AM); then: DebugTrace.lastLog.indexOf('(Calendar.AM)') >= 0
        when: DebugTrace.print('CalendarAmPm', 'Calendar.PM ', Calendar.PM); then: DebugTrace.lastLog.indexOf('(Calendar.PM)') >= 0
    }

    public def testTypesSpec() {
        when: DebugTrace.print('SqlTypes', 'Types.BIT                    ', Types.BIT                    ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.BIT)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.TINYINT                ', Types.TINYINT                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.TINYINT)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.SMALLINT               ', Types.SMALLINT               ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.SMALLINT)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.INTEGER                ', Types.INTEGER                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.INTEGER)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.BIGINT                 ', Types.BIGINT                 ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.BIGINT)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.FLOAT                  ', Types.FLOAT                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.FLOAT)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.REAL                   ', Types.REAL                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.REAL)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.DOUBLE                 ', Types.DOUBLE                 ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.DOUBLE)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.NUMERIC                ', Types.NUMERIC                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.NUMERIC)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.DECIMAL                ', Types.DECIMAL                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.DECIMAL)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.CHAR                   ', Types.CHAR                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.CHAR)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.VARCHAR                ', Types.VARCHAR                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.VARCHAR)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.LONGVARCHAR            ', Types.LONGVARCHAR            ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.LONGVARCHAR)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.DATE                   ', Types.DATE                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.DATE)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.TIME                   ', Types.TIME                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.TIME)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.TIMESTAMP              ', Types.TIMESTAMP              ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.TIMESTAMP)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.BINARY                 ', Types.BINARY                 ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.BINARY)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.VARBINARY              ', Types.VARBINARY              ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.VARBINARY)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.LONGVARBINARY          ', Types.LONGVARBINARY          ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.LONGVARBINARY)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.NULL                   ', Types.NULL                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.NULL)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.OTHER                  ', Types.OTHER                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.OTHER)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.JAVA_OBJECT            ', Types.JAVA_OBJECT            ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.JAVA_OBJECT)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.DISTINCT               ', Types.DISTINCT               ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.DISTINCT)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.STRUCT                 ', Types.STRUCT                 ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.STRUCT)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.ARRAY                  ', Types.ARRAY                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.ARRAY)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.BLOB                   ', Types.BLOB                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.BLOB)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.CLOB                   ', Types.CLOB                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.CLOB)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.REF                    ', Types.REF                    ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.REF)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.DATALINK               ', Types.DATALINK               ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.DATALINK)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.BOOLEAN                ', Types.BOOLEAN                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.BOOLEAN)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.ROWID                  ', Types.ROWID                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.ROWID)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.NCHAR                  ', Types.NCHAR                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.NCHAR)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.NVARCHAR               ', Types.NVARCHAR               ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.NVARCHAR)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.LONGNVARCHAR           ', Types.LONGNVARCHAR           ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.LONGNVARCHAR)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.NCLOB                  ', Types.NCLOB                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.NCLOB)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.SQLXML                 ', Types.SQLXML                 ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.SQLXML)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.REF_CURSOR             ', Types.REF_CURSOR             ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.REF_CURSOR)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.TIME_WITH_TIMEZONE     ', Types.TIME_WITH_TIMEZONE     ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.TIME_WITH_TIMEZONE)') >= 0
        when: DebugTrace.print('SqlTypes', 'Types.TIMESTAMP_WITH_TIMEZONE', Types.TIMESTAMP_WITH_TIMEZONE); then: DebugTrace.lastLog.indexOf('(java.sql.Types.TIMESTAMP_WITH_TIMEZONE)') >= 0
    }

    def testUserTypesSpec() {
        when: DebugTrace.print('UserType1', 'UserType1.USER_TYPE0', UserType1.USER_TYPE0); then: DebugTrace.lastLog.indexOf('(UserType1.USER_TYPE0)') >= 0
        when: DebugTrace.print('UserType1', 'UserType1.USER_TYPE1', UserType1.USER_TYPE1); then: DebugTrace.lastLog.indexOf('(UserType1.USER_TYPE1)') >= 0
        when: DebugTrace.print('UserType1', 'UserType1.USER_TYPE2', UserType1.USER_TYPE2); then: DebugTrace.lastLog.indexOf('(UserType1.USER_TYPE2)') >= 0
        when: DebugTrace.print('UserType1', 'UserType1.USER_TYPE3', UserType1.USER_TYPE3); then: DebugTrace.lastLog.indexOf('(UserType1.USER_TYPE3)') >= 0

        when: DebugTrace.print('user type2', new UserType2())
        then:
            DebugTrace.lastLog.indexOf('(UserType1.USER_TYPE1)') >= 0
            DebugTrace.lastLog.indexOf('(UserType2.USER_TYPE2)') >= 0

        when: DebugTrace.print(          'userType2' , new UserType2().userType2); then: DebugTrace.lastLog.indexOf('(UserType2.USER_TYPE2)') >= 0
        when: DebugTrace.print(         ' userType2 ', new UserType2().userType2); then: DebugTrace.lastLog.indexOf('(UserType2.USER_TYPE2)') >= 0
        when: DebugTrace.print(     ' aaa.userType2 ', new UserType2().userType2); then: DebugTrace.lastLog.indexOf('(UserType2.USER_TYPE2)') >= 0
        when: DebugTrace.print( ' aaa.bbb.userType2 ', new UserType2().userType2); then: DebugTrace.lastLog.indexOf('(UserType2.USER_TYPE2)') >= 0
        when: DebugTrace.print(' aaa.bbb. userType2 ', new UserType2().userType2); then: DebugTrace.lastLog.indexOf('(UserType2.USER_TYPE2)') >= 0
        when: DebugTrace.print(       ' 1 userType2 ', new UserType2().userType2); then: DebugTrace.lastLog.indexOf('(UserType2.USER_TYPE2)') >= 0
    }

    static class UserType1 {
        static final byte  USER_TYPE0 = (byte)0
        static final short USER_TYPE1 = (short)1
        static final int   USER_TYPE2 = 2
        static final long  USER_TYPE3 = 3L
    }

    static class UserType2 {
        static final int USER_TYPE0 = 0
        static final int USER_TYPE1 = 1
        static final int USER_TYPE2 = 2
        static final int USER_TYPE3 = 3

        int userType1 = UserType1.USER_TYPE1
        int userType2 = USER_TYPE2
    }
}
