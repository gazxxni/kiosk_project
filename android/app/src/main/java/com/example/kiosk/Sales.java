package com.example.kiosk;

public class Sales {
    private String date;
    private String itemName;
    private int quantity;
    private int totalPrice;

    public Sales(String date, String itemName, int quantity, int totalPrice) {
        this.date = date;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
