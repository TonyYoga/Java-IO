package com.telran;

import com.telran.controllers.UserController;
import com.telran.controllers.UserControllerImpl;
import com.telran.data.entity.CategoryEntity;
import com.telran.data.entity.CityEntity;
import com.telran.data.entity.Role;
import com.telran.data.entity.UserEntity;
import com.telran.data.managers.CatalogManager;
import com.telran.data.managers.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
    public static void main(String[] args) throws IOException {
        //FromClassWork
        /*
        UserController userController = new UserControllerImpl(
                new UserManager("db","users.csv","profiles.csv"));
        CatalogManager manager =
                new CatalogManager("db","cat.csv","cities.csv");
        manager.addCity(CityEntity.of("Ashdod"));
        manager.addCity(CityEntity.of("Haifa"));
        */

//        UserController userController = new UserControllerImpl(new UserManager("db", "users.csv", "profile.csv"));
        CatalogManager manager = new CatalogManager("db", "cat.csv", "cities.csv");
//        manager.addCity(CityEntity.of("Ashdod"));
//        manager.addCity(CityEntity.of("Haifa"));
//        manager.addCity(CityEntity.of("Tel Aviv"));
//        manager.addCity(CityEntity.of("Ramla"));
//        manager.removeCity("d61a8b08-62cd-4065-bd2d-bec17539bf97");
//        manager.updateCity("5bab86b5-dacf-4931-a5ab-ca4dbf6030e5", "Toronto");

//        manager.addCategory(CategoryEntity.of("Tools1"));
//        manager.addCategory(CategoryEntity.of("Toyes2"));
//        manager.addCategory(CategoryEntity.of("Goods3"));
//        manager.addCategory(CategoryEntity.of("Drinks4"));
//        manager.removeCategory("46f6b209-fb26-40bb-9cf6-14733dfb00ae");
//        manager.updateCategory("a279469a-c429-488b-bade-d637b0f900ad", "Shoes");
//        manager.getAllCities().forEach(System.out::println);
        UserManager userManager = new UserManager("db", "users.csv", "profiles.csv");
//        userManager.addUser(UserEntity.of("admin@shop.com", "000000", Role.ADMIN, userManager.addBlankProfile()));
//        userManager.addUser(UserEntity.of("userFirst@shop.com", "111111", Role.USER, userManager.addBlankProfile()));
//        userManager.addUser(UserEntity.of("userSecond@shop.com", "222222", Role.USER, userManager.addBlankProfile()));
//        userManager.addUser(UserEntity.of("user3@shop.com", "333333", Role.USER, userManager.addBlankProfile()));


        UserControllerImpl userController = new UserControllerImpl(userManager);
//        userController.changePassword("admin@shop.com", "000000", "newpassword");
//        userController.changePasswordForUser("admin@shop.com", "userFirst@shop.com", "new");
//        userController.removeUser("admin@shop.com", "userSecond@shop.com");
//        userManager.getAllUsers().forEach(System.out::println);
//        userManager.up
//        ProfileEntity profile = ProfileEntity.of("name", "lastName", "911");
//        userManager.getAllProfiles().forEach(System.out::println);
        userController.getAllUsers("admin@shop.com").forEach(System.out::println);

    }
}
