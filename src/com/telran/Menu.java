package com.telran;

import com.telran.controllers.UserController;
import com.telran.controllers.UserControllerImpl;
import com.telran.data.managers.UserManager;
import com.telran.menu.MenuBuilder;

import java.io.IOException;


public class Menu {
    public static void main(String[] args) {
        UserController userController;
        ConsoleInputOutput cio = new ConsoleInputOutput();
        try {
            userController = new UserControllerImpl(new UserManager("db", "users.csv", "profiles.csv"));
            MenuBuilder menu = new MenuBuilder(userController, cio);
            menu.showMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
