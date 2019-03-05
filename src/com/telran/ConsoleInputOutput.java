package com.telran;

import com.telran.view.InputOutput;

import java.util.Scanner;

public class ConsoleInputOutput implements InputOutput {
    private Scanner scanner = new Scanner(System.in);
    @Override
    public String readString(String promt) {
        write(promt + " > ");
        return scanner.nextLine();
    }

    @Override
    public void write(Object obj) {
        System.out.print(obj);
    }
}
