// MappingSpec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test

import java.sql.Types
import java.util.Calendar
import org.debugtrace.DebugTrace
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
        when: DebugTrace.print('calendar', Calendar.ERA                 ); then: DebugTrace.lastLog.indexOf('(Calendar.ERA)') >= 0
        when: DebugTrace.print('calendar', Calendar.YEAR                ); then: DebugTrace.lastLog.indexOf('(Calendar.YEAR)') >= 0
        when: DebugTrace.print('calendar', Calendar.MONTH               ); then: DebugTrace.lastLog.indexOf('(Calendar.MONTH)') >= 0
        when: DebugTrace.print('calendar', Calendar.WEEK_OF_YEAR        ); then: DebugTrace.lastLog.indexOf('(Calendar.WEEK_OF_YEAR)') >= 0
        when: DebugTrace.print('calendar', Calendar.WEEK_OF_MONTH       ); then: DebugTrace.lastLog.indexOf('(Calendar.WEEK_OF_MONTH)') >= 0
        when: DebugTrace.print('calendar', Calendar.DATE                ); then: DebugTrace.lastLog.indexOf('(Calendar.DATE/DAY_OF_MONTH)') >= 0
        when: DebugTrace.print('calendar', Calendar.DAY_OF_MONTH        ); then: DebugTrace.lastLog.indexOf('(Calendar.DATE/DAY_OF_MONTH)') >= 0
        when: DebugTrace.print('calendar', Calendar.DAY_OF_YEAR         ); then: DebugTrace.lastLog.indexOf('(Calendar.DAY_OF_YEAR)') >= 0
        when: DebugTrace.print('calendar', Calendar.DAY_OF_WEEK         ); then: DebugTrace.lastLog.indexOf('(Calendar.DAY_OF_WEEK)') >= 0
        when: DebugTrace.print('calendar', Calendar.DAY_OF_WEEK_IN_MONTH); then: DebugTrace.lastLog.indexOf('(Calendar.DAY_OF_WEEK_IN_MONTH)') >= 0
        when: DebugTrace.print('calendar', Calendar.AM_PM               ); then: DebugTrace.lastLog.indexOf('(Calendar.AM_PM)') >= 0
        when: DebugTrace.print('calendar', Calendar.HOUR                ); then: DebugTrace.lastLog.indexOf('(Calendar.HOUR)') >= 0
        when: DebugTrace.print('calendar', Calendar.HOUR_OF_DAY         ); then: DebugTrace.lastLog.indexOf('(Calendar.HOUR_OF_DAY)') >= 0
        when: DebugTrace.print('calendar', Calendar.MINUTE              ); then: DebugTrace.lastLog.indexOf('(Calendar.MINUTE)') >= 0
        when: DebugTrace.print('calendar', Calendar.SECOND              ); then: DebugTrace.lastLog.indexOf('(Calendar.SECOND)') >= 0
        when: DebugTrace.print('calendar', Calendar.MILLISECOND         ); then: DebugTrace.lastLog.indexOf('(Calendar.MILLISECOND)') >= 0
        when: DebugTrace.print('calendar', Calendar.ZONE_OFFSET         ); then: DebugTrace.lastLog.indexOf('(Calendar.ZONE_OFFSET)') >= 0
        when: DebugTrace.print('calendar', Calendar.DST_OFFSET          ); then: DebugTrace.lastLog.indexOf('(Calendar.DST_OFFSET)') >= 0
        when: DebugTrace.print('calendar', Calendar.FIELD_COUNT         ); then: DebugTrace.lastLog.indexOf('(Calendar.FIELD_COUNT)') >= 0
    }

    def testWeekSpec() {
        when: DebugTrace.print('calendarWeek', Calendar.SUNDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.SUNDAY)') >= 0
        when: DebugTrace.print('calendarWeek', Calendar.MONDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.MONDAY)') >= 0
        when: DebugTrace.print('calendarWeek', Calendar.TUESDAY  ); then: DebugTrace.lastLog.indexOf('(Calendar.TUESDAY)') >= 0
        when: DebugTrace.print('calendarWeek', Calendar.WEDNESDAY); then: DebugTrace.lastLog.indexOf('(Calendar.WEDNESDAY)') >= 0
        when: DebugTrace.print('calendarWeek', Calendar.THURSDAY ); then: DebugTrace.lastLog.indexOf('(Calendar.THURSDAY)') >= 0
        when: DebugTrace.print('calendarWeek', Calendar.FRIDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.FRIDAY)') >= 0
        when: DebugTrace.print('calendarWeek', Calendar.SATURDAY ); then: DebugTrace.lastLog.indexOf('(Calendar.SATURDAY)') >= 0

        when: DebugTrace.print('week', Calendar.SUNDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.SUNDAY)') >= 0
        when: DebugTrace.print('week', Calendar.MONDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.MONDAY)') >= 0
        when: DebugTrace.print('week', Calendar.TUESDAY  ); then: DebugTrace.lastLog.indexOf('(Calendar.TUESDAY)') >= 0
        when: DebugTrace.print('week', Calendar.WEDNESDAY); then: DebugTrace.lastLog.indexOf('(Calendar.WEDNESDAY)') >= 0
        when: DebugTrace.print('week', Calendar.THURSDAY ); then: DebugTrace.lastLog.indexOf('(Calendar.THURSDAY)') >= 0
        when: DebugTrace.print('week', Calendar.FRIDAY   ); then: DebugTrace.lastLog.indexOf('(Calendar.FRIDAY)') >= 0
        when: DebugTrace.print('week', Calendar.SATURDAY ); then: DebugTrace.lastLog.indexOf('(Calendar.SATURDAY)') >= 0
    }

    def testMonthSpec() {
        when: DebugTrace.print('calendarMonth', Calendar.JANUARY  ); then: DebugTrace.lastLog.indexOf('(Calendar.JANUARY)') >= 0
        when: DebugTrace.print('calendar.Month', Calendar.FEBRUARY ); then: DebugTrace.lastLog.indexOf('(Calendar.FEBRUARY)') >= 0
        when: DebugTrace.print('calendar Month', Calendar.MARCH    ); then: DebugTrace.lastLog.indexOf('(Calendar.MARCH)') >= 0
        when: DebugTrace.print('calendar. Month', Calendar.APRIL    ); then: DebugTrace.lastLog.indexOf('(Calendar.APRIL)') >= 0
        when: DebugTrace.print('calendar . Month', Calendar.MAY      ); then: DebugTrace.lastLog.indexOf('(Calendar.MAY)') >= 0
        when: DebugTrace.print(' calendarMonth', Calendar.JUNE     ); then: DebugTrace.lastLog.indexOf('(Calendar.JUNE)') >= 0
        when: DebugTrace.print('calendarMonth ', Calendar.JULY     ); then: DebugTrace.lastLog.indexOf('(Calendar.JULY)') >= 0
        when: DebugTrace.print('calendarMonth', Calendar.AUGUST   ); then: DebugTrace.lastLog.indexOf('(Calendar.AUGUST)') >= 0
        when: DebugTrace.print('calendarMonth', Calendar.SEPTEMBER); then: DebugTrace.lastLog.indexOf('(Calendar.SEPTEMBER)') >= 0
        when: DebugTrace.print('calendarMonth', Calendar.OCTOBER  ); then: DebugTrace.lastLog.indexOf('(Calendar.OCTOBER)') >= 0
        when: DebugTrace.print('calendarMonth', Calendar.NOVEMBER ); then: DebugTrace.lastLog.indexOf('(Calendar.NOVEMBER)') >= 0
        when: DebugTrace.print('calendarMonth', Calendar.DECEMBER ); then: DebugTrace.lastLog.indexOf('(Calendar.DECEMBER)') >= 0
    }

    def testAmPmSpec() {
        when: DebugTrace.print('amPm', Calendar.AM); then: DebugTrace.lastLog.indexOf('(Calendar.AM)') >= 0
        when: DebugTrace.print('ampm', Calendar.PM); then: DebugTrace.lastLog.indexOf('(Calendar.PM)') >= 0
    }

    public def testTypesSpec() {
        when: DebugTrace.print('sqlType', Types.BIT                    ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.BIT)') >= 0
        when: DebugTrace.print('sqlType', Types.TINYINT                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.TINYINT)') >= 0
        when: DebugTrace.print('sqlType', Types.SMALLINT               ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.SMALLINT)') >= 0
        when: DebugTrace.print('sqlType', Types.INTEGER                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.INTEGER)') >= 0
        when: DebugTrace.print('sqlType', Types.BIGINT                 ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.BIGINT)') >= 0
        when: DebugTrace.print('sqlType', Types.FLOAT                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.FLOAT)') >= 0
        when: DebugTrace.print('sqlType', Types.REAL                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.REAL)') >= 0
        when: DebugTrace.print('sqlType', Types.DOUBLE                 ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.DOUBLE)') >= 0
        when: DebugTrace.print('sqlType', Types.NUMERIC                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.NUMERIC)') >= 0
        when: DebugTrace.print('sqlType', Types.DECIMAL                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.DECIMAL)') >= 0
        when: DebugTrace.print('sqlType', Types.CHAR                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.CHAR)') >= 0
        when: DebugTrace.print('sqlType', Types.VARCHAR                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.VARCHAR)') >= 0
        when: DebugTrace.print('sqlType', Types.LONGVARCHAR            ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.LONGVARCHAR)') >= 0
        when: DebugTrace.print('sqlType', Types.DATE                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.DATE)') >= 0
        when: DebugTrace.print('sqlType', Types.TIME                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.TIME)') >= 0
        when: DebugTrace.print('sqlType', Types.TIMESTAMP              ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.TIMESTAMP)') >= 0
        when: DebugTrace.print('sqlType', Types.BINARY                 ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.BINARY)') >= 0
        when: DebugTrace.print('sqlType', Types.VARBINARY              ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.VARBINARY)') >= 0
        when: DebugTrace.print('sqlType', Types.LONGVARBINARY          ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.LONGVARBINARY)') >= 0
        when: DebugTrace.print('sqlType', Types.NULL                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.NULL)') >= 0
        when: DebugTrace.print('sqlType', Types.OTHER                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.OTHER)') >= 0
        when: DebugTrace.print('sqlType', Types.JAVA_OBJECT            ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.JAVA_OBJECT)') >= 0
        when: DebugTrace.print('sqlType', Types.DISTINCT               ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.DISTINCT)') >= 0
        when: DebugTrace.print('sqlType', Types.STRUCT                 ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.STRUCT)') >= 0
        when: DebugTrace.print('sqlType', Types.ARRAY                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.ARRAY)') >= 0
        when: DebugTrace.print('sqlType', Types.BLOB                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.BLOB)') >= 0
        when: DebugTrace.print('sqlType', Types.CLOB                   ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.CLOB)') >= 0
        when: DebugTrace.print('sqlType', Types.REF                    ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.REF)') >= 0
        when: DebugTrace.print('sqlType', Types.DATALINK               ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.DATALINK)') >= 0
        when: DebugTrace.print('sqlType', Types.BOOLEAN                ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.BOOLEAN)') >= 0
        when: DebugTrace.print('sqlType', Types.ROWID                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.ROWID)') >= 0
        when: DebugTrace.print('sqlType', Types.NCHAR                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.NCHAR)') >= 0
        when: DebugTrace.print('sqlType', Types.NVARCHAR               ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.NVARCHAR)') >= 0
        when: DebugTrace.print('sqlType', Types.LONGNVARCHAR           ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.LONGNVARCHAR)') >= 0
        when: DebugTrace.print('sqlType', Types.NCLOB                  ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.NCLOB)') >= 0
        when: DebugTrace.print('sqlType', Types.SQLXML                 ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.SQLXML)') >= 0
        when: DebugTrace.print('sqlType', Types.REF_CURSOR             ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.REF_CURSOR)') >= 0
        when: DebugTrace.print('sqlType', Types.TIME_WITH_TIMEZONE     ); then: DebugTrace.lastLog.indexOf('(java.sql.Types.TIME_WITH_TIMEZONE)') >= 0
        when: DebugTrace.print('sqlType', Types.TIMESTAMP_WITH_TIMEZONE); then: DebugTrace.lastLog.indexOf('(java.sql.Types.TIMESTAMP_WITH_TIMEZONE)') >= 0
    }

    def testUserTypesSpec() {
        when: DebugTrace.print('userType1', UserType1.USER_TYPE0); then: DebugTrace.lastLog.indexOf('(UserType1.USER_TYPE0)') >= 0
        when: DebugTrace.print('userType1', UserType1.USER_TYPE1); then: DebugTrace.lastLog.indexOf('(UserType1.USER_TYPE1)') >= 0
        when: DebugTrace.print('userType1', UserType1.USER_TYPE2); then: DebugTrace.lastLog.indexOf('(UserType1.USER_TYPE2)') >= 0
        when: DebugTrace.print('userType1', UserType1.USER_TYPE3); then: DebugTrace.lastLog.indexOf('(UserType1.USER_TYPE3)') >= 0

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
