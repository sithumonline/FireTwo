package com.github.sithumonline.firetwo;

public class Request {
    private String name;
    private String food;
    private String description;

    public Request() {}

    public Request(String name, String food, String description) {
        this.name = name;
        this.food = food;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getFood() {
        return food;
    }

    public String getDescription() {
        return description;
    }

}
