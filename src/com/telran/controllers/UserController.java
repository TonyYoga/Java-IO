package com.telran.controllers;

import com.telran.data.entity.CityEntity;
import com.telran.data.entity.ProfileEntity;
import com.telran.data.entity.UserEntity;

import java.util.List;

public interface UserController {
    boolean changePassword(String email, String oldPassword, String newPassword);
    boolean changePasswordForUser(String adminEmail,String userEmail,String newPassword);
    List<UserEntity> getAllUsers(String adminEmail);
    boolean addUser(String adminEmail, UserEntity user);
    UserEntity removeUser(String adminEmail, String userEmail);
    boolean updateProfile(String email, ProfileEntity profile);
    ProfileEntity removeProfileById(String adminEmail, String profileUuid);
    ProfileEntity getProfileById(String adminEmail, String profileUuid);

}
