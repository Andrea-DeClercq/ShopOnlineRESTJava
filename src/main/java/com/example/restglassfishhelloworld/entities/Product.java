package com.example.restglassfishhelloworld.entities;

import java.sql.Timestamp;

public class Product {
    private Integer productId;
    private String productName;
    private String productDescription;
    private String dossier;
    private Integer categoryId;
    private Integer inStock;
    private Double price;
    private String brand;
    private Integer nbrImage;
    private Timestamp dateAdded;

    public Product() {
    }

    public Product(Integer productId, String productName, String productDescription, String dossier, Integer categoryId, Integer inStock, Double price, String brand, Integer nbrImage, Timestamp dateAdded) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.dossier = dossier;
        this.categoryId = categoryId;
        this.inStock = inStock;
        this.price = price;
        this.brand = brand;
        this.nbrImage = nbrImage;
        this.dateAdded = dateAdded;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getDossier() {
        return dossier;
    }

    public void setDossier(String dossier) {
        this.dossier = dossier;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getNbrImage() {
        return nbrImage;
    }

    public void setNbrImage(Integer nbrImage) {
        this.nbrImage = nbrImage;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }
}
