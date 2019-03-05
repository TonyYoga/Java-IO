package com.telran.menu;

import com.telran.controllers.UserController;
import com.telran.controllers.UserControllerImpl;
import com.telran.data.entity.ProfileEntity;
import com.telran.data.entity.Role;
import com.telran.data.entity.UserEntity;
import com.telran.view.InputOutput;
import com.telran.view.Item;
import com.telran.view.Menu;

import java.io.IOException;

public class MenuBuilder {
    private UserController userController;
    private String email;
    private InputOutput cio;

    public MenuBuilder(UserController userController, InputOutput cio) {
        this.userController = userController;
        this.cio = cio;
    }

    private boolean login() throws IOException {
        email = cio.readString("Type you email");
        return ((UserControllerImpl) userController).isAdmin(email);
    }

    public void showMenu() throws IOException {
        Menu main;
        if(!login()) {
            main = new Menu("Main menu", userMenuGen(), Item.exit());
        } else {
            main = new Menu("Main menu", adminMenuGen(), userMenuGen(), Item.exit());
        }
        main.perform(cio);

    }

    private Item adminMenuGen() {

        Item exit = Item.exit();

        Item addUser = Item.of("Add user", io -> {
            UserEntity tmp = UserEntity.of(cio.readString("Type login "),
                                            cio.readString("Type password "),
                                            Role.USER);
            userController.addUser(email, tmp);
        });

        Item removeUser = Item.of("Remove user", io ->{
            userController.removeUser(email, cio.readString("Type user email "));
        });

        Item changePass = Item.of("Change password", io -> {
            userController.changePasswordForUser(email, cio.readString("Type email of user "),
                                                        cio.readString("Type new password "));
        });

        Item getAllUsers = Item.of("Get all users", io -> {
            userController.getAllUsers(email).forEach(System.out::println);
        });
        Menu userAdminMenu = new Menu("Users", getAllUsers, addUser, removeUser, changePass, exit);

        Item addCity = Item.of("Add city", io -> {});
        Item removeCity = Item.of("Remove city", io -> {});
        Item updateCity = Item.of("Update city", io -> {});
        Item getAllCities = Item.of("Get all cities", io -> {});
        Menu cityAdminMenu = new Menu("Cities", getAllCities, addCity, removeCity, updateCity, exit);

        Item getAllCategoryes  = Item.of("Get all categories", io -> {});
        Item addCategory  = Item.of("Add category", io -> {});
        Item removeCategoty  = Item.of("Remove category", io -> {});
        Item updateCategory  = Item.of("Update category", io -> {});
        Menu categoryAdminMenu = new Menu("Categoties", getAllCategoryes, addCategory, updateCategory, removeCategoty, exit);



        return new Menu("Admin menu", userAdminMenu, cityAdminMenu, categoryAdminMenu, exit);
    }

    private Item userMenuGen() {
        Item changePass = Item.of("Change pass ", io -> {
            userController.changePassword(email,
                    cio.readString("Type old password"),
                    cio.readString("Type new password"));
        });
        Item updateProfile = Item.of("Update profile", io -> {
            userController.updateProfile(email,
                    new ProfileEntity.Builder().
                            lastName(cio.readString("Type last name")).
                            name("Type name").
                            phone("Type phone").build()
                    );
        });
        Item getAllCities = Item.of("Get all cities", io -> {});
        Item getAllCats = Item.of("Get all categories", io -> {});
        Item addAdvert = Item.of("Add advert", io -> {});
        Item updateAdvert = Item.of("Update advert", io -> {});
        Item removeAdvert = Item.of("Remove advert", io -> {});
        Item getAllAdverts = Item.of("Get all adverts", io -> {});
        Item findAdvByCat = Item.of("Find advert by category", io -> {});
        Item findAdvByCity = Item.of("Find advert by city", io -> {});

        return new Menu("User menu", changePass,
                updateProfile,
                getAllCities,
                getAllCats,
                addAdvert,
                updateAdvert,
                removeAdvert,
                getAllAdverts,
                findAdvByCat,
                findAdvByCity,
                Item.exit());
    }

}
