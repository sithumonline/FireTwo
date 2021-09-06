package com.github.sithumonline.firetwo;

class Delivery {
    private String name;
    private String address;
    private String imageLink;
    private int unitPrice;

    public Delivery() {
    }

    public Delivery(String name, String address, int unitPrice) {
        this.name = name;
        this.address = address;
        this.unitPrice = unitPrice;
    }

    public Delivery(String name, String address, String imageLink, int unitPrice) {
        this.name = name;
        this.address = address;
        this.imageLink = imageLink;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getImageLink() {
        return imageLink;
    }

    public int getUnitPrice() {
        return unitPrice;
    }
}
