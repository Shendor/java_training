package org.java.training.files;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileExerciseTest {

    private FileExercise fileExercise;

    @Before
    public void setUp() throws Exception {
        fileExercise = new FileExercise();
    }

    @Test
    public void testGetFolders() throws Exception {

        Collection<String> foundFolders = fileExercise.getFolders("exercise/files");

        assertEquals(2, foundFolders.size());
        assertTrue(foundFolders.stream().allMatch(folder -> folder.contains("sample")));
    }

    @Test
    public void testGetFiles() throws Exception {
        Collection<String> foundFiles = fileExercise.findFiles("exercise/files", "file_example");

        assertEquals(2, foundFiles.size());
        assertTrue(foundFiles.stream().allMatch(folder -> folder.contains("file_example")));
    }

    @Test
    public void testFetchAllLines() throws Exception {
        Collection<String> lines = fileExercise.fetchFileLines("exercise/files/sample1/file_example.txt");

        assertEquals(2, lines.size());
        assertTrue(lines.stream().allMatch(folder -> folder.contains("line")));
    }

    @Test
    public void testSaveToFile() throws Exception {
        String filePath = "exercise/files/sample1";
        String fileName = "new_file.txt";
        fileExercise.saveLinesToFile(Arrays.asList("new line 1", "new line 2"), filePath, fileName);

        Collection<String> lines = fileExercise.fetchFileLines(filePath + "/" + fileName);
        assertEquals(2, lines.size());
        assertTrue(lines.stream().allMatch(folder -> folder.contains("line")));
    }
}