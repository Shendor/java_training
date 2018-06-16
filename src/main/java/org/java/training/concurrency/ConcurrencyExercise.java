package org.java.training.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ConcurrencyExercise {

    public static void main(String[] args) {
        ConcurrencyExercise concurrencyExercise = new ConcurrencyExercise();

        // The 'regex' folder is in 'resources' folder
        System.out.println("Parallel stream: ");
        concurrencyExercise.getTotalLinesPerLogLevel("regex",
                concurrencyExercise.new ParallelStreamLogSummaryCollectorStrategy()).getLinesPerLogLevels()
                .forEach((key, value) -> System.out.println(key + " - " + value));

        System.out.println("\nThread per log level: ");
        concurrencyExercise.getTotalLinesPerLogLevel("regex",
                concurrencyExercise.new ThreadPerLogLevelLogSummaryCollectorStrategy()).getLinesPerLogLevels()
                .forEach((key, value) -> System.out.println(key + " - " + value));

        System.out.println("\nThread per file: ");
        concurrencyExercise.getTotalLinesPerLogLevel("regex",
                concurrencyExercise.new ThreadPerFileLogSummaryCollectorStrategy()).getLinesPerLogLevels()
                .forEach((key, value) -> System.out.println(key + " - " + value));
    }

    public LogSummary getTotalLinesPerLogLevel(String rootFolder, LogSummaryCollectorStrategy strategy) {
        return strategy.getTotalLinesOfLog(rootFolder);
    }

    public interface LogSummaryCollectorStrategy {

        LogSummary getTotalLinesOfLog(String rootFolder);
    }

    /**
     * Read all files from rootFolder and extract total lines of each Log Level using parallel stream
     */
    public class ParallelStreamLogSummaryCollectorStrategy implements LogSummaryCollectorStrategy {

        @Override
        public LogSummary getTotalLinesOfLog(String rootFolder) {
            LogSummary logSummary = new LogSummary();
            //TODO write your code here. Read all files from rootFolder folder in resources, provided from the main method
            return logSummary;
        }
    }

    /**
     * Read all files from rootFolder and extract total lines of each Log Level
     * using 1 thread per log level
     */
    public class ThreadPerLogLevelLogSummaryCollectorStrategy implements LogSummaryCollectorStrategy {

        @Override
        public LogSummary getTotalLinesOfLog(String rootFolder) {
            LogSummary logSummary = new LogSummary();
            //TODO write your code here. Read all files from rootFolder folder in resources, provided from the main method
            return logSummary;
        }
    }

    /**
     * Read all files from rootFolder and extract total lines of each Log Level
     * using 1 thread per file
     */
    public class ThreadPerFileLogSummaryCollectorStrategy implements LogSummaryCollectorStrategy {

        @Override
        public LogSummary getTotalLinesOfLog(String rootFolder) {
            LogSummary logSummary = new LogSummary();
            //TODO write your code here. Read all files from rootFolder folder in resources, provided from the main method
            return logSummary;
        }
    }

    public class LogSummary {
        private Map<LogLevel, Long> linesPerLogLevels;

        public LogSummary() {
            linesPerLogLevels = new HashMap<>();
            Stream.of(LogLevel.values()).forEach(item -> linesPerLogLevels.put(item, 0L));
        }

        public synchronized void incrementTotalLinesOfLogLevel(LogLevel logLevel) {
            linesPerLogLevels.put(logLevel, linesPerLogLevels.get(logLevel) + 1);
        }

        public void incrementTotalLinesOfLogLevel(LogLevel logLevel, long value) {
            linesPerLogLevels.put(logLevel, linesPerLogLevels.get(logLevel) + value);
        }

        public long getTotalLinesOfLogLevel(LogLevel logLevel) {
            return linesPerLogLevels.get(logLevel);
        }

        public Map<LogLevel, Long> getLinesPerLogLevels() {
            return linesPerLogLevels;
        }
    }

    public enum LogLevel {
        TRACE,
        DEBUG,
        INFO,
        WARNING,
        ERROR;

        public static Optional<LogLevel> getContainingLogLevelFrom(String logEntry) {
            return Stream.of(LogLevel.values())
                    .filter(logLevel -> logEntry.contains(logLevel.toString()))
                    .findFirst();
        }
    }

}
