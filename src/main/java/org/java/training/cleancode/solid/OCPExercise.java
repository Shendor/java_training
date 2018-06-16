package org.java.training.cleancode.solid;

import java.util.HashMap;
import java.util.stream.Stream;

public class OCPExercise {

    /*
     * This is a simple converter class which converts comma-separated message into a common FormattedMessage.
     * Formatted message is just a Map where key is field name and value is the value of the field.
     * Comma-separated message consists of name of field and its value in such format: field1=value1.
     * A sample of comma-separated message: field1=abc,id=43,name=tro lo lolo,age=99
     * The method convertFrom already implements a conversion from comma-separated message into FormattedMessage.
     * After few sprints, your customer tells that his business is growing fast and for now he wants you to implement another message conversion.
     * This time his message can come to your precious converter in Json format so you need to be able to convert it too
     * into FormattedMessage. Oh, and just 'for fun' he wants you to add support of a xml message as well as he thinks that
     * you might be too bored of doing just json format.
     *
     * NOTE: For Json conversion to your FormattedMessage use ObjectMapper class.
     * For XML...standard java library. Google it if you don't know how to use it :)
     * Class must conform SRP and OCP.
     * As you can see convertFrom accepts message as Object which can be anything
     * so you need to verify whether it's json or xml using simple regex in order to understand how to convert it.
     * See: sample-message.xml where element name is field name and value is content if it is a simple text.
     * See: sample-message.json where you need to extract only fields with values as simple text.
     * OCPExerciseTest has 3 simple tests for 3 different messages which should pass.
     * */

    public FormattedMessage convertFrom(Object message) {
        FormattedMessage formattedMessage = new FormattedMessage();
        if (String.valueOf(message).matches("([\\w\\d]+=[\\w\\d\\s]+,?)+")) {
            Stream.of(String.valueOf(message).split(","))
                    .forEach(fieldValuePair -> {
                        String[] splitFieldValue = fieldValuePair.split("=");
                        formattedMessage.put(splitFieldValue[0], splitFieldValue[1]);
                    });

        }
        return formattedMessage;
    }

    public class FormattedMessage extends HashMap<String, String> {

    }

}
