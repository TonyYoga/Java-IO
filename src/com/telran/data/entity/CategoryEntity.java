package com.telran.data.entity;

import java.util.Objects;
import java.util.UUID;

public class CategoryEntity {
    private final UUID uuid;
    private final String name;

    private CategoryEntity(String name) {
        uuid = UUID.randomUUID();
        this.name = name;
    }

    private CategoryEntity(String uuid, String name) {
        this.uuid = UUID.fromString(uuid);
        this.name = name;
    }

    public String getUuid() {
        return uuid.toString();
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return uuid.toString() + "," + name;
    }

    public static CategoryEntity of(String name){
        Objects.requireNonNull(name);
        return new CategoryEntity(name);
    }

    public static CategoryEntity fromString(String data) {
        Objects.requireNonNull(data);
        String[] arr = data.split(",");
        if (arr.length != 2) {
            throw new IllegalArgumentException("Wrong data format!");
        }
        return new CategoryEntity(arr[0], arr[1]);
    }
}
