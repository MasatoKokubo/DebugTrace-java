// DebugTraceSpec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.util.logging.LogManager

import org.debugtrace.DebugTrace
import spock.lang.*

/**
 * @since 3.1.0
 */
@Unroll
class LoggersSpec extends Specification {
    def setupSpec() {
    }

    def cleanupSpec() {
        DebugTrace.initClass('DebugTrace')
    }

    // enterStringSpec
    def "loggersSpec #loggerName"(String loggerName) {
        setup:
            if (loggerName == 'Flogger' || loggerName == 'Jdk') {
                def propertyPath = "src/test/resources/logging_${loggerName}.properties"
                System.setProperty('java.util.logging.config.file', propertyPath)
                LogManager.getLogManager().readConfiguration()
            }
            DebugTrace.initClass("DebugTrace_${loggerName}")

        when:
            def logMessage = "This is a ${loggerName} log."
            DebugTrace.print(logMessage);
            def filePath = "logs/${loggerName}.log"
            def log = ""
            Files.lines(Paths.get(filePath), Charset.forName('UTF-8')) each {
                log += it
                log += '\n'
            }
            System.out.print(log)

        then:
            log.contains(logMessage)

        where:
            loggerName|_
            'Flogger'|_
            'Jdk'    |_
            'Log4j'  |_
            'Log4j2' |_
            'SLF4J'  |_ // and Logback
    }

}
