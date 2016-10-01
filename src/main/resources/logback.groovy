import ch.qos.logback.core.*;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;

appender(name="CONSOLE", clazz=ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "date=[%date{ISO8601}] level=[%level] %logger{15}: message=%msg\n"
    }
}

// Set to TRACE to log all HTTP requests and responses
logger(name="io.gatling.http.ahc", level=DEBUG)
logger(name="io.gatling.http.response", level=DEBUG)

root(level=INFO, appenderNames=["CONSOLE"])