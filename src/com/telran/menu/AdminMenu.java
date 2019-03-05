package com.telran.menu;

import com.telran.controllers.UserController;
import com.telran.controllers.UserControllerImpl;
import com.telran.data.entity.Role;
import com.telran.data.entity.UserEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminMenu {
    private UserController userController;
    private String adminMail;
    private Map<String, Menu> adminMenu;
    private BufferedReader br;

    public AdminMenu(UserController userController, BufferedReader br) {
        this.userController = userController;
        adminMenu = new HashMap<>();
        this.br = br;
    }

    private boolean login() throws IOException {
        System.out.println("Type email");
        adminMail = br.readLine();
        return ((UserControllerImpl) userController).isAdmin(adminMail);
    }

    public void showMenu() throws IOException {
        if (!login()) {
            System.out.println("Wrong admin e-mail!");
            return;
        }

        adminMenu.put("1", new MenyAdd());
        adminMenu.put("2", new MenyRemove());
        adminMenu.put("3", new MenyGetAll());
        adminMenu.put("4", new MenyExit());
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Type number of command");
            adminMenu.forEach((k, v) -> System.out.println(k + ". " + v.getName()));
            String key = br.readLine();
            if (adminMenu.containsKey(key)) {
                adminMenu.get(key).execute();
                isExit = adminMenu.get(key).isExit();
            } else {
                System.out.println("Wrong command!");
            }
        }
    }

    private abstract class MenuCommand {
        private String name;
        private boolean isExit;


        public MenuCommand(String name, boolean isExit) {
            this.name = name;
            this.isExit = isExit;
        }

        public String getName() {
            return name;
        }

        public boolean isExit() {
            return isExit;
        }
    }

    private interface Menu {
        boolean execute() throws IOException;

        String getName();

        boolean isExit();
    }


    private class MenyAdd extends MenuCommand implements Menu {

        public MenyAdd() {
            super("Add", false);
        }

        @Override
        public boolean execute() throws IOException {
            System.out.print("Type email: ");
            String email = br.readLine();
            System.out.print("Type password: ");
            String pass = br.readLine();
            userController.addUser(adminMail, UserEntity.of(email, pass, Role.USER));
            return true;
        }
    }

    private class MenyRemove extends MenuCommand implements Menu {

        public MenyRemove() {
            super("Remove", false);
        }

        @Override
        public boolean execute() throws IOException {
            System.out.print("Type email: ");
            String email = br.readLine();
            userController.removeUser(adminMail, email);
            return true;
        }
    }

    private class MenyGetAll extends MenuCommand implements Menu {

        public MenyGetAll() {
            super("Get all", false);
        }

        @Override
        public boolean execute() {
            userController.getAllUsers(adminMail).forEach(System.out::println);
            return true;
        }
    }

    private class MenyExit extends MenuCommand implements Menu {

        public MenyExit() {
            super("Exit", true);
        }

        @Override
        public boolean execute() {
            return false;
        }
    }

}


