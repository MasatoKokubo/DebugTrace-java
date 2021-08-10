// PropertyV1Spec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test

import java.sql.Types
import java.util.Calendar
import org.debugtrace.DebugTrace
import spock.lang.*

/**
 * Property version 1 Test.
 *
 * @since 3.0.0
 * @author Masato Kokubo
 */
@Unroll
class PropertyV1Spec extends Specification {
    def setupSpec() {
        DebugTrace.initClass("DebugTrace_PropertyV1Spec")
    }

    def cleanupSpec() {
        DebugTrace.initClass("DebugTrace")
    }

    def "initClassSpec"() {
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
        DebugTrace.nonOutputString        == '<NonPrint>'
        DebugTrace.cyclicReferenceString  == '<CyclicReference>'
        DebugTrace.varNameValueSeparator  == ' <= '
        DebugTrace.keyValueSeparator      == ':: '
        DebugTrace.printSuffixFormat      == ' (%3$s:%4$d)'
        DebugTrace.sizeFormat             == 'size:%1d'   // default
        DebugTrace.minimumOutputSize      == 5            // default
        DebugTrace.lengthFormat           == 'length:%1d' // default
        DebugTrace.minimumOutputLength    == 5            // default
        DebugTrace.utilDateFormat         == 'yyyy/MM/dd HH;mm;ss.SSSxxx'
        DebugTrace.sqlDateFormat          == 'yyyy/MM/ddxxx'
        DebugTrace.timeFormat             == 'HH;mm;ss.SSSxxx'
        DebugTrace.timestampFormat        == 'yyyy/MM/dd HH;mm;ss.SSSSSSSSSxxx'
        DebugTrace.localDateFormat        == 'yyyy/MM/dd'
        DebugTrace.offsetTimeFormat       == 'HH;mm;ss.SSSSSSSSSxxx'
        DebugTrace.localDateTimeFormat    == 'yyyy/MM/dd HH;mm;ss.SSSSSSSSS'
        DebugTrace.offsetDateTimeFormat   == 'yyyy/MM/dd HH;mm;ss.SSSSSSSSSxxx'
        DebugTrace.zonedDateTimeFormat    == 'yyyy/MM/dd HH;mm;ss.SSSSSSSSSxxx VV'
        DebugTrace.instantFormat          == 'yyyy/MM/dd HH;mm;ss.SSSSSSSSSX'
        DebugTrace.logDateTimeFormat      == 'yyyy/MM/dd HH;mm;ss.SSSxxx'
        DebugTrace.maximumDataOutputWidth == 70 // default
        DebugTrace.collectionLimit        == 8
        DebugTrace.byteArrayLimit         == 5
        DebugTrace.stringLimit            == 10
        DebugTrace.reflectionNestLimit    == 2
        DebugTrace.nonOutputProperties    == ['org.debugtrace.test.PropertySpec$Point#y']
        DebugTrace.defaultPackage         == 'org.debugtrace.test'
        DebugTrace.defaultPackageString   == '~~~'
    // 3.1.1
    //  DebugTrace.reflectionClasses      == ['org.debugtrace.test.PropertySpec$Point3']
        DebugTrace.reflectionClasses      == ['org.debugtrace.test.PropertySpec$Point3'] as Set
    ////
    }
}
