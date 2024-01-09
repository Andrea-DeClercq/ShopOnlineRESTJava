package com.example.restglassfishhelloworld.entities;

public class Cart {
    private int idCart;
    private int idUser;
    private int idProduct;
    private int quantity;
    private boolean payed;
    private boolean confirmed;

    public Cart() {
    }

    public Cart(int idCart, int idUser, int idProduct, int quantity, boolean payed, boolean confirmed) {
        this.idCart = idCart;
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.payed = payed;
        this.confirmed = confirmed;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}

