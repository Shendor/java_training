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

public class RegexpExerciseAnswersTest {

    private RegexpExercise regexpExercise;

    @Before
    public void setUp() throws Exception {
        regexpExercise = new RegexpExercise();
    }

    @Test
    public void testMatchSimpleString() throws Exception {
        // match the exact text: text to test
        assertTrue(regexpExercise.isMatchPattern("text to test", "text\\s+to\\s+test"));

        Stream.of("".split(";"))
                .filter(item -> item.trim().length() > 0)
                .map(item -> Stream.of(item.split(",")).collect(Collectors.toList()))
                .collect(Collectors.toList());


        String numberInStreetPattern = "[a-zA-Z]+\\s+[0-9]+";
        assertTrue(regexpExercise.isMatchPattern("Street 23", numberInStreetPattern));
        assertFalse(regexpExercise.isMatchPattern("Street ABC", numberInStreetPattern));

        String addressPattern = "[a-zA-Z\\s]+[0-9]+[a-zA-Z\\s]*";
        assertTrue(regexpExercise.isMatchPattern("Baldur gate 666 str", addressPattern));
        assertTrue(regexpExercise.isMatchPattern("no name street 1", addressPattern));
        assertFalse(regexpExercise.isMatchPattern("invalid street", addressPattern));

        // match the text 'field=' followed by any number
        String fieldValuePattern = "field=\\d+(\\.\\d+)?";
        assertTrue(regexpExercise.isMatchPattern("field=1.3", fieldValuePattern));
        assertTrue(regexpExercise.isMatchPattern("field=25.889", fieldValuePattern));
        assertTrue(regexpExercise.isMatchPattern("field=157", fieldValuePattern));
        assertFalse(regexpExercise.isMatchPattern("field=1abc", fieldValuePattern));

        // match the text 'key=' followed by any letter or number, comma separated
        String keyValuesPattern = "key=(\\w+\\,?)+";
        assertTrue(regexpExercise.isMatchPattern("key=v1,key=abcd", keyValuesPattern));
        assertTrue(regexpExercise.isMatchPattern("key=54", keyValuesPattern));
        assertFalse(regexpExercise.isMatchPattern("key=", keyValuesPattern));

        String codePattern = "code:[0-9]{4}";
        assertTrue(regexpExercise.isMatchPattern("code:4501", codePattern));
        assertTrue(regexpExercise.isMatchPattern("code:2219", codePattern));
        assertFalse(regexpExercise.isMatchPattern("code:786", codePattern));

        String emailPattern = "[^\\d][\\w\\S\\.]+@[\\w\\S]+\\.[a-z]{2,3}";
        assertTrue(regexpExercise.isMatchPattern("r.d@vocalink.com", emailPattern));
        assertTrue(regexpExercise.isMatchPattern("rd3@vocalink.com", emailPattern));
        assertFalse(regexpExercise.isMatchPattern("5rd@vocalink.com", emailPattern));
        assertFalse(regexpExercise.isMatchPattern("r d@vocalink.com", emailPattern));
        assertFalse(regexpExercise.isMatchPattern("rd@vocalink.c", emailPattern));
        assertFalse(regexpExercise.isMatchPattern("rd@vocalin k.com", emailPattern));

        String pointPattern = "([x|y]{1}=[0-9]+,?){2}";
        assertTrue(regexpExercise.isMatchPattern("x=1,y=5", pointPattern));
        assertTrue(regexpExercise.isMatchPattern("y=3,x=22", pointPattern));
        assertFalse(regexpExercise.isMatchPattern("ys=3,x=22", pointPattern));
        assertFalse(regexpExercise.isMatchPattern("y=3,x=22,z=1", pointPattern));

        // String multilineTextPattern = "(?m)^.*$";
        //assertTrue(regexpExercise.isMatchPattern("value1 \n value2", multilineTextPattern));
    }

    @Test
    public void testExtractGroupsFromString() throws Exception {
        String addressNumberPattern = "\\d+";
        List<String> addressNumber = regexpExercise.extractStringsByPattern("Some street 43", addressNumberPattern);
        assertEquals(1, addressNumber.size());
        assertTrue(addressNumber.contains("43"));

        String pointPattern = "[x|y]{1}=[0-9]+";
        List<String> xyPoints = regexpExercise.extractStringsByPattern("x=1 y=5 x=3 z=7", pointPattern);
        assertTrue(xyPoints.contains("x=1") && xyPoints.contains("y=5") && xyPoints.contains("x=3") && !xyPoints.contains("z=7"));

        String dataInBracketsPattern = "\\([\\w\\s,]+\\)";
        List<String> dataInBrackets =
                regexpExercise.extractStringsByPattern("Payments for banks (A, B) are rejected and payment for banks (C) are accepted",
                        dataInBracketsPattern);
        assertEquals(2, dataInBrackets.size());
        assertTrue(dataInBrackets.contains("(A, B)") && dataInBrackets.contains("(C)"));

        String dataInQuotesPattern = "\\\"[\\w\\s,]+\\\"";
        List<String> dataInQuotes =
                regexpExercise.extractStringsByPattern("text1 \"text2\", \"\"text 3\"\" text4",
                        dataInQuotesPattern);
        assertEquals(2, dataInQuotes.size());
        assertTrue(dataInQuotes.contains("\"text2\"") && dataInQuotes.contains("\"text 3\""));

        String commaSeparatedPattern = "\\s*([^\\,]+)";
        List<String> commaSeparatedGroups =
                regexpExercise.extractStringsByPattern("text1, text2, text3=5 4, text=\"data\"",
                        commaSeparatedPattern);
        assertEquals(4, commaSeparatedGroups.size());
        assertTrue(commaSeparatedGroups.contains("text1") && commaSeparatedGroups.contains("text2") &&
                commaSeparatedGroups.contains("text3=5 4") && commaSeparatedGroups.contains("text=\"data\""));

        String dataInTagsPattern = "<p>(\\w+)</p>";
        List<String> dataInTags =
                regexpExercise.extractStringsByPattern("<i>text1</i> <dir><p>text2</p></dir>",
                        dataInTagsPattern);
        assertEquals(1, dataInTags.size());
        assertTrue(dataInTags.contains("text2") && !dataInTags.contains("<p>text2</p>"));

        //replace spaces between text and = sign
        String pattern = "(\\w+)(\\s*)(=)(\\s*)(\\w+)";
        System.out.println("filed1 =1 field2 = 2  field3= value 3".replaceAll(pattern, "$1$3$5"));
    }

    @Test
    public void testExtractDataFromFile() throws Exception {
        //extract all msgid's data from canonical messages of mtid 9804
        // the example of these messages in sample.log file which starts from: GWTR0010 Bert Package Request: ['translate_to_external'
        // hint: \.* might not work for 'any chars' pattern so you probably need to define your own pattern for this to include the characters
        // which are met in the searching string, like comma, quote or colon
        Collection<String> msgIds = extractDataFromLogByRegex("\\.*'mtid', 9804[,\\s\\]\\[\\\"\\'\\w\\d\\-\\'\\:]*msgid', ([\\d\\w]+)");
        msgIds.forEach(System.out::println);
        assertEquals(4, msgIds.size());

        // extract all dates inside CreDtTm tag of external_ok_response of iso20022 and fusm.001.001.01
        Collection<String> dates = extractDataFromLogByRegex("external_ok_response[,\\s\\]\\[\\\"\\'\\w\\d\\-\\:\\<\\>\\/]+fusm.001.001.01[,\\s\\]\\[\\\"\\'\\w\\d\\-\\:\\<\\>\\/]+<CreDtTm>([\\d\\-\\w\\:]+)</CreDtTm>");
        dates.forEach(System.out::println);
        assertEquals(32, dates.size());
    }

    @Test
    public void testContainsDataFromFile() throws Exception {
        // check if the log has an error like 'Mandatory field <something> is null
        assertTrue(containsDataFromLog("Mandatory field \\[\\w+\\] is null"));

        // check if the log has an external_error_response for code 650 and iso20022
        assertTrue(containsDataFromLog("external_error_response', \\[650[,\\s\\]\\[\\\"\\'\\w\\d\\-\\:\\<\\>\\/]+format', iso20022"));
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