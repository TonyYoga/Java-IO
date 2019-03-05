package com.telran.data.managers;

import com.telran.data.entity.CategoryEntity;
import com.telran.data.entity.CityEntity;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.telran.data.entity.utils.Utils.save;
import static com.telran.data.entity.utils.Utils.saveAll;


public class CatalogManager {
    private Path rootDirectory;
    private Path catFile;
    private Path cityFile;

    public CatalogManager(String rootDirectory, String catFileName, String cityFileName) throws IOException {
        this.rootDirectory = Path.of(rootDirectory);
        if(!Files.exists(this.rootDirectory)){
            Files.createDirectory(this.rootDirectory);
        }else if(Files.isRegularFile(this.rootDirectory)){
            throw new IOException(rootDirectory + " is a file!");
        }

        catFile = Path.of(this.rootDirectory.toString(),catFileName);

        if(Files.exists(catFile)){
            if(Files.isDirectory(catFile)){
                throw new IOException(catFileName + " is directory");
            }
        }else{
            Files.createFile(catFile);
        }

        cityFile = Path.of(this.rootDirectory.toString(),cityFileName);
        if(Files.exists(cityFile)){
            if(Files.isDirectory(cityFile)){
                throw new IOException(cityFileName + " is directory!");
            }
        }else{
            Files.createFile(cityFile);
        }
    }


    public boolean addCity(CityEntity city) throws IOException {
        Objects.requireNonNull(city);
        return save(cityFile, city.toString());
    }

    public boolean updateCity(String uuid, String cityName) throws IOException {
        List<CityEntity> cities = getAllCitiesWithoutNext(uuid);
        cities.add(CityEntity.fromString(uuid + "," + cityName));
        saveAll(cityFile,cities);
        return true;
    }
    public boolean removeCity(String uuid) {
        try {
            return saveAll(cityFile, getAllCitiesWithoutNext(uuid));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CityEntity> getAllCities() throws IOException {
        try (Stream<String> stream = Files.lines(cityFile)){
            return stream.map(CityEntity::fromString)
                    .collect(Collectors.toList());
        }
    }

    private List<CityEntity> getAllCitiesWithoutNext(String uuidFilter) throws IOException {
        try (Stream<String> stream = Files.lines(cityFile)) {
            return stream.map(CityEntity::fromString)
                    .filter(v -> !uuidFilter.equals(v.getUuid()))
                    .collect(Collectors.toList());
        }

    }

    public boolean addCategory(CategoryEntity categoryEntity) {
        Objects.requireNonNull(categoryEntity);
        return save(catFile, categoryEntity.toString());
    }

    public boolean updateCategory(String uuid, String name) throws IOException {
        List<CategoryEntity> cats = getAllCategoriesWithoutNExt(uuid);
        cats.add(CategoryEntity.fromString(uuid + "," + name));
        return saveAll(catFile, cats);
    }

    public boolean removeCategory(String uuid) {
        try {
            return saveAll(catFile, getAllCategoriesWithoutNExt(uuid));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CategoryEntity> getAllCategories() throws IOException {
        try (Stream<String> stream = Files.lines(catFile)) {
            return stream.map(CategoryEntity::fromString)
                    .collect(Collectors.toList());
        }
    }

    private List<CategoryEntity> getAllCategoriesWithoutNExt(String uuidFilter) throws IOException {
        try (Stream<String> stream = Files.lines(catFile)) {
            return stream.map(CategoryEntity::fromString)
                    .filter(cat -> !cat.getUuid().equals(uuidFilter))
                    .collect(Collectors.toList());
        }
    }
}
