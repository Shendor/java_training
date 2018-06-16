package org.java.training.files;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileTraining {

    public static void main(String[] args) throws IOException {
    }

    private static void writeToFile() throws IOException {
        Files.write(getFile("empty_file.txt").toPath(),
                Stream.of("new line 1", "new line 2", "new line 3").collect(Collectors.toList()));
    }

    private static void writeToFileUsingOldApproach() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile("empty_file.txt")))) {
            Stream.of("new line 1", "new line 2", "new line 3")
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            // log an error
                            e.printStackTrace();
                        }
                    });
            writer.flush();
        }
    }

    private static void readFileUsingOldApproach() throws Exception {
        try (BufferedReader inputStream = new BufferedReader(new FileReader(getFile("training_sample1.txt")))) {
            String line;
            while ((line = inputStream.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    private static void readFileUsingStream() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFile("training_sample1.txt")))) {
            reader.lines().forEach(System.out::println);
        }
    }

    private static void readFile() throws IOException {
        List<String> fileContent = Files.readAllLines(getFile("training_sample1.txt").toPath());

        fileContent.forEach(System.out::println);
    }

    private static void callNewStyleFileInteraction() throws Exception {
        Files.createDirectories(Paths.get("folder1/folder2/folder3"));
        Path filePath = Paths.get("folder1/folder2/folder3/file1.txt");
        Files.write(filePath, Arrays.asList("line3", "line4"),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            reader.lines().forEach(System.out::println);
        }

        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(System.out::println);
        }

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath)) {
            bufferedWriter.write("new line1");
            bufferedWriter.write("\n");
            bufferedWriter.write("new line2");
        }
    }

    private static void callOldStyleFileInteraction() throws Exception {
        File dir = new File("test-dir");
        File file = new File("test-dir/file1.txt");
        if (!dir.exists()) {
            if (dir.mkdir()) {
                if (!file.exists() && file.createNewFile()) {

                }
            }
        }
        try(FileWriter w = new FileWriter(file)){
            w.write("line1");
            w.write("\n");
            w.write("line2");
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    private static File getFile(String fileName) {
        ClassLoader classLoader = FileTraining.class.getClassLoader();
        return new File(classLoader.getResource("training/files/" + fileName).getFile());
    }

    private static Collection<String> getAllFileNamesFrom(URI uri) throws Exception {
        return Files.walk(Paths.get(uri))
                .filter(path -> path.toFile().isFile())
                .map(path -> path.toFile().getName())
                .collect(Collectors.toList());
    }

    private static URI getResourceUri(String rootFolder) throws URISyntaxException {
        return Thread.currentThread().getContextClassLoader().getResource(rootFolder).toURI();
    }

    private static String getResourcePath(String rootFolder) throws URISyntaxException {
        return new File(Thread.currentThread().getContextClassLoader().getResource(rootFolder).getFile()).getAbsolutePath();
    }
}
