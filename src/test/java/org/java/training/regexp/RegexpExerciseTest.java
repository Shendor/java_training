package org.java.training.regexp;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.*;

public class RegexpExerciseTest {

    private RegexpExercise regexpExercise;

    @Before
    public void setUp() throws Exception {
        regexpExercise = new RegexpExercise();
    }

    @Test
    public void testMatchSimpleString() throws Exception {
        // match the exact text to test which always must have word 'test'
        assertTrue(regexpExercise.isMatchPattern("text to test", ""));

        // match a simple street name with a number
        String numberInStreetPattern = "";
        assertTrue(regexpExercise.isMatchPattern("Street 23", numberInStreetPattern));
        assertFalse(regexpExercise.isMatchPattern("Street ABC", numberInStreetPattern));

        // match a simple address
        String addressPattern = "";
        assertTrue(regexpExercise.isMatchPattern("Baldur gate 666 str", addressPattern));
        assertTrue(regexpExercise.isMatchPattern("no name street 1", addressPattern));
        assertFalse(regexpExercise.isMatchPattern("invalid street", addressPattern));

        // match the text 'field=' followed by any float number
        String fieldValuePattern = "";
        assertTrue(regexpExercise.isMatchPattern("field=1.3", fieldValuePattern));
        assertTrue(regexpExercise.isMatchPattern("field=25.889", fieldValuePattern));
        assertTrue(regexpExercise.isMatchPattern("field=157", fieldValuePattern));
        assertFalse(regexpExercise.isMatchPattern("field=1abc", fieldValuePattern));

        // match the text 'key=' followed by any letter or number, comma separated
        String keyValuesPattern = "";
        assertTrue(regexpExercise.isMatchPattern("key=v1,key=abcd", keyValuesPattern));
        assertTrue(regexpExercise.isMatchPattern("key=54", keyValuesPattern));
        assertFalse(regexpExercise.isMatchPattern("key=", keyValuesPattern));

        // match the text 'code:' followed by any number with length 4
        String codePattern = "";
        assertTrue(regexpExercise.isMatchPattern("code:4501", codePattern));
        assertTrue(regexpExercise.isMatchPattern("code:2219", codePattern));
        assertFalse(regexpExercise.isMatchPattern("code:786", codePattern));

        // match the email pattern which must not start from a number
        String emailPattern = "";
        assertTrue(regexpExercise.isMatchPattern("r.d@vocalink.com", emailPattern));
        assertTrue(regexpExercise.isMatchPattern("rd3@vocalink.com", emailPattern));
        assertFalse(regexpExercise.isMatchPattern("5rd@vocalink.com", emailPattern));
        assertFalse(regexpExercise.isMatchPattern("r d@vocalink.com", emailPattern));
        assertFalse(regexpExercise.isMatchPattern("rd@vocalink.c", emailPattern));
        assertFalse(regexpExercise.isMatchPattern("rd@vocalin k.com", emailPattern));

        // match the 2D point which has a simple integer number
        String pointPattern = "";
        assertTrue(regexpExercise.isMatchPattern("x=1,y=5", pointPattern));
        assertTrue(regexpExercise.isMatchPattern("y=3,x=22", pointPattern));
        assertFalse(regexpExercise.isMatchPattern("ys=3,x=22", pointPattern));
        assertFalse(regexpExercise.isMatchPattern("y=3,x=22,z=1", pointPattern));
    }

    @Test
    public void testExtractGroupsFromString() {
        String addressNumberPattern = "";
        List<String> addressNumber = regexpExercise.extractStringsByPattern("Some street 43", addressNumberPattern);
        assertEquals(1, addressNumber.size());
        assertTrue(addressNumber.contains("43"));

        // extract x and y coordinates only
        String pointPattern = "";
        List<String> xyPoints = regexpExercise.extractStringsByPattern("x=1 y=5 x=3 z=7", pointPattern);
        assertTrue(xyPoints.contains("x=1") && xyPoints.contains("y=5") && xyPoints.contains("x=3") && !xyPoints.contains("z=7"));

        String dataInBracketsPattern = "";
        List<String> dataInBrackets =
                regexpExercise.extractStringsByPattern("Payments for banks (A, B) are rejected and payment for banks (C) is accepted",
                        dataInBracketsPattern);
        assertEquals(2, dataInBrackets.size());
        assertTrue(dataInBrackets.contains("(A, B)") && dataInBrackets.contains("(C)"));

        String dataInQuotesPattern = "";
        List<String> dataInQuotes =
                regexpExercise.extractStringsByPattern("text1 \"text2\", \"\"text 3\"\" text4",
                        dataInQuotesPattern);
        assertEquals(2, dataInQuotes.size());
        assertTrue(dataInQuotes.contains("\"text2\"") && dataInQuotes.contains("\"text 3\""));

        String commaSeparatedPattern = "";
        List<String> commaSeparatedGroups =
                regexpExercise.extractStringsByPattern("text1, text2, text3=5 4, text=\"data\"",
                        commaSeparatedPattern);
        assertEquals(4, commaSeparatedGroups.size());
        assertTrue(commaSeparatedGroups.contains("text1") && commaSeparatedGroups.contains("text2") &&
                commaSeparatedGroups.contains("text3=5 4") && commaSeparatedGroups.contains("text=\"data\""));

        String dataInTagsPattern = "";
        List<String> dataInTags =
                regexpExercise.extractStringsByPattern("<i>text1</i> <dir><p>text2</p></dir>",
                        dataInTagsPattern);
        assertEquals(1, dataInTags.size());
        assertTrue(dataInTags.contains("text2") && !dataInTags.contains("<p>text2</p>"));

    }

    @Test
    public void testExtractDataFromFile() throws Exception {
        //extract all msgid's data from canonical messages of mtid 9804
        // the example of these messages in sample.log file which starts from: GWTR0010 Bert Package Request: ['translate_to_external'
        // hint: \.* might not work for 'any chars' pattern so you probably need to define your own pattern for this to include the characters
        // which are met in the searching string, like comma, quote or colon
        String regexToExtractMsgIds = "";
        Collection<String> msgIds = extractDataFromLogByRegex(regexToExtractMsgIds);
        msgIds.forEach(System.out::println);
        assertEquals(4, msgIds.size());

        // extract all dates inside CreDtTm tag of external_ok_response of iso20022 and fusm.001.001.01
        String regexToExtractDates = "";
        Collection<String> dates = extractDataFromLogByRegex(regexToExtractDates);
        dates.forEach(System.out::println);
        assertEquals(32, dates.size());
    }

    @Test
    public void testContainsDataFromFile() throws Exception {
        // check if the log has an error like 'Mandatory field <something> is null
        String regexToCheckError = "write your regex here";
        assertTrue(containsDataFromLog(regexToCheckError));

        // check if the log has an external_error_response for code 650 and iso20022
        String regexToCheck650Error = "write your regex here";
        assertTrue(containsDataFromLog(regexToCheck650Error));
    }

    private Collection<String> extractDataFromLogByRegex(String regex) throws Exception {
        try (Stream<String> stream = Files.lines(Paths.get(getResourceUri("regex/files/sample.log")))) {
            return stream
                    .map(line -> regexpExercise.extractStringsByPattern(line, regex))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
    }

    private boolean containsDataFromLog(String regex) throws Exception {
        try (Stream<String> stream = Files.lines(Paths.get(getResourceUri("regex/files/sample.log")))) {
            return stream.anyMatch(line -> regexpExercise.isContainData(line, regex));
        }
    }

    private URI getResourceUri(String rootFolder) throws URISyntaxException {
        return Thread.currentThread().getContextClassLoader().getResource(rootFolder).toURI();
    }
}