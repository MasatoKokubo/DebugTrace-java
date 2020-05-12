// PropertySpec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test;

import java.sql.*;
import java.time.*
import org.debugtrace.DebugTrace;
import spock.lang.*

/**
 * Property Test.
 *
 * @since 3.0.0
 * @author Masato Kokubo
 */
@Unroll
class PropertySpec extends Specification {
    static TimeZone defaultTimeZone = TimeZone.default

    @Shared Node           cyclicNode
    @Shared Node           node2
    @Shared Node           node3
    @Shared Node           node4
    @Shared java.sql.Date  sqlDate
    @Shared java.util.Date utilDate
    @Shared Time           time
    @Shared Timestamp      timestamp
    @Shared LocalDate      localDate
    @Shared LocalTime      localTime
    @Shared OffsetTime     offsetTime
    @Shared LocalDateTime  localDateTime
    @Shared OffsetDateTime offsetDateTime
    @Shared ZonedDateTime  zonedDateTime
    @Shared Instant        instant

    static class Point {
        int x, y

        Point(int x, int y) {
            this.x = x
            this.y = y
        }
    }

    static class Point3 {
        int x, y, z

        Point3(int x, int y, int z) {
            this.x = x
            this.y = y
            this.z = z
        }

        @Override
        public String toString() {
            return "(${x}, ${y}, ${z})"
        }
    }

    static class Point3_ extends Point3 {
        Point3_(int x, int y, int z) {
            super(x, y, z)
        }
    }

    static class Node {
        Node next
    }

    def setupSpec() {
        TimeZone.default = TimeZone.getTimeZone("Asia/Tokyo")
        DebugTrace.initClass('DebugTrace_PropertySpec')

        cyclicNode = new Node()
        cyclicNode.next = cyclicNode
        node2 = new Node()
        node2.next = new Node()
        node3 = new Node()
        node3.next = new Node()
        node3.next.next = new Node()
        node4 = new Node()
        node4.next = new Node()
        node4.next.next = new Node()
        node4.next.next.next = new Node()

        localDate      = LocalDate.of(2020, 5, 10)
        localTime      = LocalTime.of(23, 58, 59, 987_654_321)
        offsetTime     = localTime.atOffset(ZoneOffset.ofHours(6))
        localDateTime  = LocalDateTime.of(localDate, localTime)
        sqlDate        = java.sql.Date.valueOf(localDate)
        utilDate       = new java.util.Date(sqlDate.getTime())
        time           = Time.valueOf(localTime)
        timestamp      = Timestamp.valueOf(localDateTime)
        offsetDateTime = OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(6))
        zonedDateTime  = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Tokyo"))
        instant        = Instant.ofEpochSecond(timestamp.getTime())
    }

    def cleanupSpec() {
        DebugTrace.initClass('DebugTrace')
        TimeZone.default = defaultTimeZone
    }

    def initClassSpec() {
        expect:
            DebugTrace.logger.getClass().name == 'org.debugtrace.logger.Std$Out'

            DebugTrace.logLevel               == 'Info'
            DebugTrace.enterFormat            == '_Enter_ %1$s.%2$s (%3$s:%4$d)'
            DebugTrace.leaveFormat            == '_Leave_ %1$s.%2$s (%3$s:%4$d) duration: %5$tT.%5$tN'
            DebugTrace.threadBoundaryFormat   == '_Thread_ %1$s'
            DebugTrace.classBoundaryFormat    == '_  %1$s _'
            DebugTrace.indentString           == '||'
            DebugTrace.dataIndentString       == '``'
            DebugTrace.limitString            == '<Limit>'
            DebugTrace.nonOutputString        == '<NonOutput>'
            DebugTrace.cyclicReferenceString  == '<CyclicReference>'
            DebugTrace.varNameValueSeparator  == ' <= '
            DebugTrace.keyValueSeparator      == ':: '
            DebugTrace.printSuffixFormat      == ' (%3$s::%4$d)'
            DebugTrace.sizeFormat             == '_size_:%1d'
            DebugTrace.minimumOutputSize      == 3
            DebugTrace.lengthFormat           == '_length_:%1d'
            DebugTrace.minimumOutputLength    == 4
            DebugTrace.utilDateFormat         == 'yyyy/MM/dd HH;mm;ss.SSSxxx'
            DebugTrace.sqlDateFormat          == 'yyyy/MM/ddxxx'
            DebugTrace.timeFormat             == 'HH;mm;ss.SSSxxx'
            DebugTrace.timestampFormat        == 'yyyy/MM/dd HH;mm;ss.SSSSSSSSSxxx'
            DebugTrace.localDateFormat        == 'yyyy/MM/dd'
            DebugTrace.localTimeFormat        == 'HH;mm;ss.SSSSSSSSS'
            DebugTrace.offsetTimeFormat       == 'HH;mm;ss.SSSSSSSSSxxx'
            DebugTrace.localDateTimeFormat    == 'yyyy/MM/dd HH;mm;ss.SSSSSSSSS'
            DebugTrace.offsetDateTimeFormat   == 'yyyy/MM/dd HH;mm;ss.SSSSSSSSSxxx'
            DebugTrace.zonedDateTimeFormat    == 'yyyy/MM/dd HH;mm;ss.SSSSSSSSSxxx VV'
            DebugTrace.instantFormat          == 'yyyy/MM/dd HH;mm;ss.SSSSSSSSSX'
            DebugTrace.logDateTimeFormat      == 'yyyy/MM/dd HH;mm;ss.SSSxxx'
            DebugTrace.maximumDataOutputWidth == 70
            DebugTrace.collectionLimit        == 8
            DebugTrace.byteArrayLimit         == 5
            DebugTrace.stringLimit            == 10
            DebugTrace.reflectionNestLimit    == 2
            DebugTrace.nonOutputProperties    == ['org.debugtrace.test.PropertySpec$Point#y']
            DebugTrace.defaultPackage         == 'org.debugtrace.test'
            DebugTrace.defaultPackageString   == '~~~'
            DebugTrace.reflectionClasses      == ['org.debugtrace.test.PropertySpec$Point3']
    }

    def "propertiesSpec #testProperties"(String testProperties, Object value, String output) {
        // enterFormat, defaultPackage, defaultPackageString
        when:
            DebugTrace.enter()
        then:
            DebugTrace.lastLog.contains('_Enter_ ~~~.PropertySpec.')

        when:
            DebugTrace.print('testProperties', testProperties)
            DebugTrace.print('value', value)
        then:
            DebugTrace.lastLog.contains(output)

        // leaveFormat, defaultPackage, defaultPackageString
        when:
            DebugTrace.leave()
        then:
            DebugTrace.lastLog.contains('_Leave_ ~~~.PropertySpec.')

        where:
            testProperties                                           |value                       |output
            'indentString, varNameValueSeparator, printSuffixFormat' |1                           |'|value <= 1 (PropertySpec.groovy::'
            'dataIndentString, lengthFormat'                         |['A'*10, 'B'*10, 'C'*10]    |'(_length_:10)'
            'collectionLimit(1)'                                     |[1, 2, 3, 4, 5, 6, 7, 8]    |'[1, 2, 3, 4, 5, 6, 7, 8]'
            'collectionLimit(2), limitString'                        |[1, 2, 3, 4, 5, 6, 7, 8, 9] |'[1, 2, 3, 4, 5, 6, 7, 8, <Limit>]'
            'nonOutputString, nonOutputProperties, keyValueSeparator'|new Point(1, 2)             |'y:: <NonOutput>'
            'cyclicReferenceString'                                  |cyclicNode                  |'next:: <CyclicReference>'
            'minimumOutputSize(1)'                                   |[1, 2]                      |'(ArrayList)[1, 2]'
            'minimumOutputSize(2), sizeFormat'                       |[1, 2, 3]                   |'(ArrayList _size_:3)[1, 2, 3]'
            'minimumOutputLength(1)'                                 |"ABC"                       |'= "ABC"'
            'minimumOutputLength(2), lengthFormat'                   |"ABCD"                      |'= (_length_:4)"ABCD"'
            'utilDateFormat'                                         |utilDate                    |' (java.util.Date)2020/05/10 00;00;00.000+09:00 '
            'sqlDateFormat'                                          |sqlDate                     |'2020/05/10+09:00 '
            'timeFormat'                                             |time                        |'23;58;59.000+09:00 '
            'timestampFormat'                                        |timestamp                   |'2020/05/10 23;58;59.987654321+09:00 '
            'localDateFormat'                                        |localDate                   |'(LocalDate)2020/05/10 '
            'localTimeFormat'                                        |localTime                   |'(LocalTime)23;58;59.987654321 '
            'offsetTimeFormat'                                       |offsetTime                  |'(OffsetTime)23;58;59.987654321+06:00 '
            'localDateTimeFormat'                                    |localDateTime               |'(LocalDateTime)2020/05/10 23;58;59.987654321 '
            'offsetDateTimeFormat'                                   |offsetDateTime              |'(OffsetDateTime)2020/05/10 23;58;59.987654321+06:00 '
            'zonedDateTimeFormat'                                    |zonedDateTime               |'(ZonedDateTime)2020/05/10 23;58;59.987654321+09:00 Asia/Tokyo '
            'instantFormat'                                          |instant                     |'(Instant)+52327/04/19 07;19;47.000000000Z'
            'byteArrayLimit(1)'                                      |[1, 2, 3, 4, 5] as byte[]   |'(byte[5])[01 02 03 04 05]'
            'byteArrayLimit(2)'                                      |[1, 2, 3, 4, 5, 6] as byte[]|'(byte[6])[01 02 03 04 05 <Limit>]'
            'stringLimit(1)'                                         |'ABCDEFGHIJ'                |'(_length_:10)"ABCDEFGHIJ"'
            'stringLimit(2)'                                         |'ABCDEFGHIJK'               |'(_length_:11)"ABCDEFGHIJ<Limit>"'
            'reflectionNestLimit(1)'                                 |node2                       |'next:: null'
            'reflectionNestLimit(2)'                                 |node3                       |'next:: <Limit>'
            'reflectionClasses(1)'                                   |new Point3_(1, 2, 3)        |'(1, 2, 3)'
            'reflectionClasses(2)'                                   |new Point3(1, 2, 3)         |'x:: 1, y:: 2, z:: 3'
    }
}
