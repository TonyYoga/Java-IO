package com.telran.view;

import java.util.function.Function;

public interface InputOutput {
    String readString(String promt);

    void write(Object obj);

    default void writeLine(Object obj) {
        write(obj + "\n");
    }

    default <R> R readObject(String prompt, String error, Function<String, R> mapper) {
        while (true) {
            String line = readString(prompt);

            try {
                return mapper.apply(line);
            } catch (RuntimeException e) {
                write(error);
            }
        }
    }

    default int readInt(String prompt) {
        return readObject(prompt, "It's not a number", Integer::parseInt);
    }

    default int readInt(String prompt, int min, int max) {
        return readObject(prompt, String.format("It's not int in the range [%d, %d]", min, max), str ->{
            int value = Integer.parseInt(str);
            if (value < min || value > max) {
                throw new NumberFormatException();
            }
            return value;
        });
    }
}
