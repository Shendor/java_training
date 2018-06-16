package org.java.training.files;

import java.io.BufferedWriter;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileExerciseAnswers {

    public Collection<String> getFolders(String rootFolder) {
        try (Stream<Path> stream = Files.list(Paths.get(getResourceUri(rootFolder)))) {
            return stream
                    .map(String::valueOf)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Collection<String> findFiles(String rootFolder, String fileName) {
        int maxDepth = 5;
        try {
            try (Stream<Path> stream = Files.find(Paths.get(getResourceUri(rootFolder)), maxDepth,
                    (path, attr) -> String.valueOf(path).contains(fileName))) {
                return stream
                        .map(String::valueOf)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Collection<String> fetchFileLines(String filePath) {
        try (Stream<String> stream = Files.lines(Paths.get(getResourceUri(filePath)))) {
            return stream.collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void saveLinesToFile(Collection<String> lines, String filePath, String fileName) {
        //Files.write(Paths.get(filePath), (Iterable<String>)lines.stream().iterator);
        Path path = createFileAndGetPath(filePath, fileName);
        if (path != null) {
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Path createFileAndGetPath(String filePath, String fileName) {
        Path fileFullPath = null;
        try {
            fileFullPath = Paths.get(getResourcePath(filePath) + "/" + fileName);
            Files.deleteIfExists(fileFullPath);
            Files.createFile(fileFullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileFullPath;
    }

    public URI getResourceUri(String rootFolder) throws URISyntaxException {
        return Thread.currentThread().getContextClassLoader().getResource(rootFolder).toURI();
    }

    public String getResourcePath(String rootFolder) throws URISyntaxException {
        return new File(Thread.currentThread().getContextClassLoader().getResource(rootFolder).getFile()).getAbsolutePath();
    }
}
