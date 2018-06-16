package org.java.training.cleancode.codesmells;

public class PrimitiveObsessionExample {

    private static final int INFO = 1;
    private static final int DEBUG = 2;
    private static final int ERROR = 3;

    private int id;
    private int age;

    public void set(String name, Object value) {
        if (name.equals("id")) {
            this.id = Integer.parseInt(value.toString());
        } else if (name.equals("name")) {
            this.age = Integer.parseInt(value.toString());
        }
    }

    public void log(int logLevel, String message) {
        if (logLevel == INFO) {

        } else if (logLevel == DEBUG) {

        } else if (logLevel == ERROR) {

        }
    }
}