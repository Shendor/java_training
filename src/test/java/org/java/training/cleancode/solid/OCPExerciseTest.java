package org.java.training.cleancode.solid;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class OCPExerciseTest {

    private OCPExercise ocpExercise;

    @Before
    public void setUp() throws Exception {
        ocpExercise = new OCPExercise();
    }

    @Test
    public void testConvertMessage_WhenMessageIsKeyValuePair() {
        String message = "id=32,name=trololo,age=99,realm=smelly hills";
        OCPExercise.FormattedMessage formattedMessage = ocpExercise.convertFrom(message);

        assertFormattedMessage(formattedMessage);
    }

    @Test
    public void testConvertMessage_WhenMessageIsJson() throws Exception {
        String message = getFileContent("/solid/sample-message.json");

        OCPExercise.FormattedMessage formattedMessage = ocpExercise.convertFrom(message);

        assertFormattedMessage(formattedMessage);
    }

    @Test
    public void testConvertMessage_WhenMessageIsXML() throws Exception {
        String message = getFileContent("/solid/sample-message.xml");

        OCPExercise.FormattedMessage formattedMessage = ocpExercise.convertFrom(message);

        assertFormattedMessage(formattedMessage);
    }

    private String getFileContent(String filePath) throws URISyntaxException, IOException {
        Path pathToJson = Paths.get(getClass().getResource(filePath).toURI());
        return Files.lines(pathToJson).collect(Collectors.joining());
    }

    private void assertFormattedMessage(OCPExercise.FormattedMessage formattedMessage) {
        assertEquals(4, formattedMessage.size());
        assertEquals("32", formattedMessage.get("id"));
        assertEquals("trololo", formattedMessage.get("name"));
        assertEquals("99", formattedMessage.get("age"));
        assertEquals("smelly hills", formattedMessage.get("realm"));
    }
}