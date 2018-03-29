package com.example.red.magazine.serviceproviders.model;

/**
 * Created by RED on 10/10/2017.
 */

public class RestaurantItmes {
    private String id;
    private String name;
    private String category_name;
    private String package_name;
    private String type_name;
    private String supplier_name;
    private String description;
    private String photo_path;

    public RestaurantItmes(String id, String name, String category_name, String package_name, String type_name, String supplier_name, String description, String photo_path) {
        this.id = id;
        this.name = name;
        this.category_name = category_name;
        this.package_name = package_name;
        this.type_name = type_name;
        this.supplier_name = supplier_name;
        this.description = description;
        this.photo_path = photo_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
