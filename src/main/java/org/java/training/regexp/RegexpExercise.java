package org.java.training.regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpExercise {

    /*
    Reference:  https://www.regular-expressions.info

    ^	Matches the beginning of the line.
    $	Matches the end of the line.
    .	Matches any single character except newline. Using m option allows it to match the newline as well.
    [...]	Matches any single character in brackets.
    [^...]	Matches any single character not in brackets.
    x*	Matches 0 or more occurrences of the preceding expression x.
    x+	Matches 1 or more occurrences of the preceding expression x.
    x?	Matches 0 or 1 occurrences of the preceding expression x.
    x{n}	Matches exactly n number of occurrences of the preceding expression.
    x{n, m}	Matches at least n and at most m occurrences of expression x.
    a|b	 Matches either a or b.
    (x)	Groups regular expressions and remembers the matched text.
    \w	Matches the word characters. Same as [a-zA-Z]
    \W	Matches the nonword characters.
    \s	Matches the whitespace. Equivalent to [\t\n\r\f].
    \S	Matches the nonwhitespace.
    \d	Matches the digits. Equivalent to [0-9].
    \D	Matches the nondigits.
    \b	Matches the word boundaries when outside the brackets. Matches the backspace (0x08) when inside the brackets.
    \B	Matches the nonword boundaries.
    \n, \t, etc.	Matches newlines, carriage returns, tabs, etc.
    \Q	Escape (quote) all characters up to \E.
    \E	Ends quoting begun with \Q.
    \\"  Matches double quotes
    \\[  Matches square brackets
    \\{   Matches parenthesis
    \\\\   Matches slash
     */

    /**
     * Check if stringToCheck is exactly matches the regexpPattern
     */
    public boolean isMatchPattern(String stringToCheck, String regexpPattern) {
        Pattern pattern = Pattern.compile(regexpPattern);
        Matcher matcher = pattern.matcher(stringToCheck);

        return matcher.matches();
    }

    /**
     * Check if stringToCheck matches the regexpPattern
     */
    public boolean isContainData(String stringToCheck, String regexpPattern) {
        Pattern pattern = Pattern.compile(regexpPattern);
        Matcher matcher = pattern.matcher(stringToCheck);

        return matcher.find();
    }

    /**
     * Extracts all possible groups from originString defined in regexpExtractionPattern
     */
    public List<String> extractStringsByPattern(String originString, String regexpExtractionPattern) {
        Pattern pattern = Pattern.compile(regexpExtractionPattern);
        Matcher matcher = pattern.matcher(originString);

        List<String> extractedStrings = new ArrayList<>();
        while (matcher.find()) {
            // first group is the whole matched string but we're interested in the last inner string to extract
            if (matcher.groupCount() == 1) {
                extractedStrings.add(matcher.group(1));
            } else {
                extractedStrings.add(matcher.group());
            }
        }
        return extractedStrings;
    }

}
