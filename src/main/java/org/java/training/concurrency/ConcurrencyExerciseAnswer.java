package org.java.training.concurrency;

import org.java.training.files.FileExerciseAnswers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcurrencyExerciseAnswer {

    public static void main(String[] args) {
        ConcurrencyExerciseAnswer concurrencyExercise = new ConcurrencyExerciseAnswer();

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
            FileExerciseAnswers service = new FileExerciseAnswers();
            LogSummary logSummary = new LogSummary();
            try {
                Files.walk(Paths.get(service.getResourceUri(rootFolder)))
                        .filter(path -> path.toFile().isFile() && path.toString().contains(".log"))
                        .parallel()
                        .forEach(file -> {
                            try (Stream<String> lines = Files.lines(file)) {
                                lines.forEach(line -> {
                                    LogLevel.getContainingLogLevelFrom(line)
                                            .ifPresent(logSummary::incrementTotalLinesOfLogLevel);
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }
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
            FileExerciseAnswers service = new FileExerciseAnswers();
            LogSummary logSummary = new LogSummary();
            ExecutorService executorService = Executors.newFixedThreadPool(logSummary.getLinesPerLogLevels().size());
            logSummary.getLinesPerLogLevels().keySet().forEach(logLevel -> {
                executorService.execute(() -> {
                    try {
                        Files.walk(Paths.get(service.getResourceUri(rootFolder)))
                                .filter(path -> path.toFile().isFile() && path.toString().contains(".log"))
                                .forEach(file -> {
                                    try (Stream<String> lines = Files.lines(file)) {
                                        long count = lines.filter(line -> line.contains(logLevel.toString())).count();
                                        logSummary.incrementTotalLinesOfLogLevel(logLevel, count);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            executorService.shutdown();
            try {
                executorService.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            FileExerciseAnswers service = new FileExerciseAnswers();
            LogSummary logSummary = new LogSummary();
            try {
                List<Path> logFiles =
                        Files.walk(Paths.get(service.getResourceUri(rootFolder)))
                                .filter(path -> path.toFile().isFile() && path.toString().contains(".log"))
                                .collect(Collectors.toList());
                ExecutorService executorService = Executors.newCachedThreadPool();
                logFiles.forEach(logFile -> {
                    executorService.execute(() -> {
                        try (Stream<String> lines = Files.lines(logFile)) {
                            lines.forEach(line -> {
                                LogLevel.getContainingLogLevelFrom(line)
                                        .ifPresent(logSummary::incrementTotalLinesOfLogLevel);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                });
                executorService.shutdown();
                try {
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
