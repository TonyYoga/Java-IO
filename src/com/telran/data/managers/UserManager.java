package com.telran.data.managers;

import com.telran.data.entity.ProfileEntity;
import com.telran.data.entity.UserEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.telran.data.entity.utils.Utils.save;
import static com.telran.data.entity.utils.Utils.saveAll;

public class UserManager {
    private Path rootDir;
    private Path userFile;
    private Path profileFile;

    public UserManager(String rootDir, String userFile, String profileFile) throws IOException {
        this.rootDir = Path.of(rootDir);
        if(!Files.exists(this.rootDir)){
            Files.createDirectory(this.rootDir);
        }else if(Files.isRegularFile(this.rootDir)){
            throw new IOException(rootDir + " is a file!");
        }

        this.userFile = Path.of(this.rootDir.toString(),userFile);

        if(Files.exists(this.userFile)){
            if(Files.isDirectory(this.userFile)){
                throw new IOException(userFile + " is directory");
            }
        }else{
            Files.createFile(this.userFile);
        }

        this.profileFile = Path.of(this.rootDir.toString(),profileFile);
        if(Files.exists(this.profileFile)){
            if(Files.isDirectory(this.profileFile)){
                throw new IOException(profileFile + " is directory!");
            }
        }else{
            Files.createFile(this.profileFile);
        }
    }

    public boolean addUser(UserEntity user) throws IOException {
        Objects.requireNonNull(user);
        return save(userFile, user.toString());
    }

    public UserEntity getUserByEmail(String email) throws IOException {
        Objects.requireNonNull(email);
        try(Stream<String> stream = Files.lines(userFile)){
            return stream.map(UserEntity::fromString)
                    .filter(user -> user.getEmail().equals(email))
                    .findFirst().orElse(null);
        }
    }

    public List<UserEntity> getAllUsers() throws IOException {
        try(Stream<String> stream = Files.lines(userFile)){
            return stream.map(UserEntity::fromString)
                    .collect(Collectors.toList());
        }
    }

    private List<UserEntity> getAllUsersWithoutNext(String email) throws IOException {
        try (Stream<String> stream = Files.lines(userFile)) {
            return stream.map(UserEntity::fromString)
                    .filter(user -> !user.getEmail().equals(email))
                    .collect(Collectors.toList());
        }
    }

    public boolean updateUser(UserEntity userEntity) throws IOException {
        Objects.requireNonNull(userEntity);
        List<UserEntity> list = getAllUsersWithoutNext(userEntity.getEmail());
        list.add(userEntity);
        saveAll(userFile,list);
        return true;
    }

    public boolean removeUser(String email) throws IOException {
        Objects.requireNonNull(email);
        List<UserEntity> list = getAllUsersWithoutNext(email);
        saveAll(userFile,list);
        return true;
    }

    public boolean addProfile(ProfileEntity profile) throws IOException {
        Objects.requireNonNull(profile);
        return save(profileFile, profile.toString());
    }

    public ProfileEntity getProfileById(String uuid) throws IOException {
        Objects.requireNonNull(uuid);
        try (Stream<String> stream = Files.lines(profileFile)) {
            return stream.map(ProfileEntity::fromString)
                    .filter(profile -> profile.getUuid().equals(uuid))
                    .findFirst().orElse(null);
        }
    }


    private List<ProfileEntity> getAllProfilesWithoutNext(String uuid) throws IOException {
        Objects.requireNonNull(uuid);
        try (Stream<String> stream = Files.lines(profileFile)) {
            return stream.map(ProfileEntity::fromString)
                    .filter(profile -> !profile.getUuid().equals(uuid))
                    .collect(Collectors.toList());
        }
    }

    public List<ProfileEntity> getAllProfiles() throws IOException {
        try (Stream<String> stream = Files.lines(profileFile)) {
            return stream.map(ProfileEntity::fromString)
                    .collect(Collectors.toList());
        }
    }


    public boolean updateProfile(ProfileEntity profile) throws IOException {
        Objects.requireNonNull(profile);
        List<ProfileEntity> profiles = getAllProfilesWithoutNext(profile.getUuid());
        profiles.add(profile);
        saveAll(profileFile, profiles);
        return true;
    }

    public boolean removeProfile(String uuid) throws IOException {
        Objects.requireNonNull(uuid);
        List<ProfileEntity> list = getAllProfilesWithoutNext(uuid);
        saveAll(profileFile, list);
        return true;
    }

    public String addBlankProfile() throws IOException {
        ProfileEntity curr = new ProfileEntity.Builder().build();
        addProfile(curr);
        return curr.getUuid();
    }
}
