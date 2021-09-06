package com.github.sithumonline.firetwo;

class Rent {
    private String name;
    private String address;
    private String items;
    private String imageLink;
    private int hourlyRental;

    public Rent() {
    }

    public Rent(String name, String address, String items, int hourlyRental) {
        this.name = name;
        this.address = address;
        this.items = items;
        this.hourlyRental = hourlyRental;
    }

    public Rent(String name, String address, String items, String imageLink, int hourlyRental) {
        this.name = name;
        this.address = address;
        this.items = items;
        this.imageLink = imageLink;
        this.hourlyRental = hourlyRental;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getItems() {
        return items;
    }

    public String getImageLink() {
        return imageLink;
    }

    public int getHourlyRental() {
        return hourlyRental;
    }
}
