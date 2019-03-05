package com.telran.controllers;

import com.telran.data.entity.ProfileEntity;
import com.telran.data.entity.Role;
import com.telran.data.entity.UserEntity;
import com.telran.data.managers.UserManager;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserControllerImpl implements UserController {
    private UserManager manager;

    public UserControllerImpl(UserManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        if (!isValid(email, oldPassword, newPassword)) {
            return false;
        }
        try {
            UserEntity curr = manager.getUserByEmail(email);
            if (curr.getPassword().equals(oldPassword)) {
                return manager.updateUser(UserEntity.of(email, newPassword, curr.getRole(), curr.getProfileUuid()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean changePasswordForUser(String adminEmail, String userEmail, String newPassword) {
        if (!isValid(adminEmail, userEmail, newPassword) && !isAdmin(adminEmail)) {
            return false;
        }
        try {
            UserEntity curr = manager.getUserByEmail(userEmail);
            if (curr != null) {
                return manager.updateUser(UserEntity.of(userEmail, newPassword, curr.getRole(), curr.getProfileUuid()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public List<UserEntity> getAllUsers(String adminEmail) {
        if (isAdmin(adminEmail)) {
            try {
                return manager.getAllUsers();
            } catch (IOException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public boolean addUser(String adminEmail, UserEntity user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(adminEmail);
        if (isAdmin(adminEmail)) {
            ProfileEntity empty = new ProfileEntity.Builder().build();
            try {
                manager.addProfile(empty);
                UserEntity forSave = UserEntity.of(user.getEmail(),
                        user.getPassword(),
                        user.getRole(),
                        empty.getUuid());
                manager.addUser(forSave);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public UserEntity removeUser(String adminEmail, String userEmail) {
        if (!isValid(adminEmail, userEmail) && !isAdmin(adminEmail)) {
            return null;
        }
        UserEntity curr = null;
        try {
            curr = manager.getUserByEmail(userEmail);
            if (curr != null) {
                manager.removeProfile(curr.getProfileUuid());
                manager.removeUser(userEmail);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return curr;
    }

    @Override
    public boolean updateProfile(String email, ProfileEntity profile) {
        Objects.requireNonNull(profile);
        if (!isValid(email)) {
            return false;
        }
        UserEntity curr = null;
        try {
            curr = manager.getUserByEmail(email);
            if (curr != null) {
                manager.removeProfile(curr.getProfileUuid());
                manager.addProfile(profile);
                return manager.updateUser(UserEntity.of(curr.getEmail(), curr.getPassword(), curr.getRole(), profile.getUuid()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ProfileEntity removeProfileById(String adminEmail, String profileUuid) {
        if (!isValid(adminEmail, profileUuid) && !isAdmin(adminEmail)) {
            return null;
        }
        ProfileEntity currProfile = null;
        try {
            currProfile = manager.getProfileById(profileUuid);
            if (currProfile != null) {
                manager.removeProfile(profileUuid);
                UserEntity currUser = manager.getAllUsers().stream()
                        .filter(user -> user.getProfileUuid().equals(profileUuid))
                        .findFirst().orElse(null);
                if (currUser != null) {
                    manager.updateUser(UserEntity.of(currUser.getEmail(),
                            currUser.getPassword(),
                            currUser.getRole(),
                            new ProfileEntity.Builder().build().getUuid()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return currProfile;
    }

    @Override
    public ProfileEntity getProfileById(String adminEmail, String profileUuid) {
        if (!isValid(adminEmail, profileUuid) && !isAdmin(adminEmail)) {
            return null;
        }
        try {
            return manager.getProfileById(profileUuid);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isAdmin(String email) {
        Objects.requireNonNull(email);
        try {
            UserEntity user = manager.getUserByEmail(email);
            if (user.getRole() != Role.ADMIN) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean isValid(String... args) {
        for (int i = 0; i < args.length; i++) {
            Objects.requireNonNull(args[i]);
            if (args[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
