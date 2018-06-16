package org.java.training.regexp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class RegexpTrainingTest {

    private RegexpExercise regexpExercise;

    @Before
    public void setUp() {
        regexpExercise = new RegexpExercise();
    }

    @Test
    public void testMatchSimpleString() {

        Assert.assertTrue(regexpExercise.isMatchPattern("awriut545hawi tj Participant 02000010 has been provisioned",
                "[\\w\\s]+Participant 02000010[\\w\\s]+"));

        Assert.assertTrue(regexpExercise.isContainData("awriuthawi tj \n Participant 02000010 has been provisioned",
                "Participant 02\\d{6} has been provisioned"));

        Assert.assertTrue(regexpExercise.isMatchPattern("123 Orcs House, Mordor, Middleearth, WW66 6X",
                "[\\d\\w\\s]+,\\s?\\w+,\\s?\\w+,\\s?\\w{2}\\d{1,2}\\s?\\d{1}\\w{1}"));
    }

    @Test
    public void testExtractGroupsFromString() {
        System.out.println(regexpExercise.extractStringsByPattern(" ff 365,2,1 fff", "\\.*(\\d+)\\.*"));

        String transactionsPattern = "\\((?:B1|B2)+:[\\w\\d]+\\)-TRN\\d+";
        List<String> transactions = regexpExercise.extractStringsByPattern("transactions (B1:B2)-TRN001, (B1:B3)-HXX007, (B2:B7)-TRN304are sent",
                transactionsPattern);
        System.out.println(transactions);
        assertTrue(transactions.contains("(B1:B2)-TRN001") && transactions.contains("(B2:B7)-TRN304"));

        String xmlTagPattern = "<MsgId>([a-zA-Z0-9]+)</MsgId>";
        List<String> xmlData = regexpExercise.extractStringsByPattern(
                "<GrpHdr> " +
                        "     <MsgId>MSG0000199032XX888</MsgId> " +
                        "     <CreDtTm>01-01-2017</CreDtTm> " +
                        "</GrpHdr>", xmlTagPattern);
        System.out.println(xmlData);
        assertTrue(xmlData.contains("MSG0000199032XX888"));

        String pattern = "(\\w+)(\\s*)(=)(\\s*)(\\d+)";
        System.out.println("filed1 =1 field2 = 2  field3= value 3".replace(pattern, "$1$3$5"));

        System.out.println(Arrays.toString("text1<fg213>text2<ggg>text3".split("<[\\w\\d]+>")));
    }

}