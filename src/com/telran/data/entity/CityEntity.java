package com.telran.data.entity;

import java.util.Objects;
import java.util.UUID;

public class CityEntity {
    private final UUID uuid;
    private final String name;

    private CityEntity(String name) {
        this.name = name;
        uuid = UUID.randomUUID();
    }

    private CityEntity(String uuid, String name) {
        this.uuid = UUID.fromString(uuid);
        this.name = name;
    }

    public String getUuid() {
        return uuid.toString();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return uuid.toString() + "," + name;
    }

    public static CityEntity of(String name){
        Objects.requireNonNull(name);
        return new CityEntity(name);
    }

    public static CityEntity fromString(String data){
        Objects.requireNonNull(data);
        String[] arr = data.split(",");
        if(arr.length!=2){
            throw new IllegalArgumentException("Wrong data format!");
        }
        return new CityEntity(arr[0],arr[1]);
    }
}
