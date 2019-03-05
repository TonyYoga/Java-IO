package com.telran.data.entity;

import java.util.Objects;
import java.util.UUID;

public class ProfileEntity {
    private UUID uuid;
    private String name;
    private String lastName;
    private String phone;

    private ProfileEntity(String name, String lastName, String phone) {
        uuid = UUID.randomUUID();
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }

    private ProfileEntity(String uuid, String name, String lastName, String phone) {
        this.uuid = UUID.fromString(uuid);
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String getUuid() {
        return uuid.toString();
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String toString(){
        return uuid.toString() + "," + name + "," + lastName + "," + phone;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileEntity that = (ProfileEntity) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(uuid);
    }

    public static ProfileEntity fromString(String data){
        Objects.requireNonNull(data);
        String[] arr = data.split(",");
        if (arr.length != 4) {
            throw new IllegalArgumentException("Wrong data format");
        }
        return new ProfileEntity(arr[0],arr[1],arr[2],arr[3]);
    }

    public static class Builder{
        private String name;
        private String lastName;
        private String phone;
        private String uuid;

        public Builder() {
            name = "NoName";
            lastName = "NoLastName";
            phone = "NoPhone";
        }

        public Builder uuid(String uuid) {
            Objects.requireNonNull(uuid);
            uuid = uuid.trim();
            if (uuid.isEmpty()) {
                throw new IllegalArgumentException("UUID can't be empty!");
            }
            this.uuid = uuid;
            return this;
        }

        public Builder name(String name){
            Objects.requireNonNull(name);
            name = name.trim();
            if(name.isEmpty()){
                throw new IllegalArgumentException("Name can't be empty!");
            }
            this.name = name;
            return this;
        }

        public Builder lastName(String lastName){
            Objects.requireNonNull(lastName);
            lastName = lastName.trim();
            if (lastName.isEmpty()) {
                throw new IllegalArgumentException("Last name can't be empty!");
            }
            this.lastName = lastName;
            return this;
        }

        public Builder phone(String phone) {
            Objects.requireNonNull(phone);
            phone = phone.trim();
            if (phone.isEmpty()) {
                throw new IllegalArgumentException("Phone can't be empty!");
            }
            this.phone = phone;
            return this;
        }

        public ProfileEntity build() {
            if(uuid == null){
                return new ProfileEntity(name,lastName,phone);
            }else{
                return new ProfileEntity(uuid,name,lastName,phone);
            }
        }
    }
}





