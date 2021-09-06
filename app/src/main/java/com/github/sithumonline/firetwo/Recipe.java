package com.github.sithumonline.firetwo;

class Recipe {
    private String name;
    private String steps;
    private String ingredients;
    private String imageLink;

    public Recipe() {
    }

    public Recipe(String name, String steps, String ingredients) {
        this.name = name;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    public Recipe(String name, String steps, String ingredients, String imageLink) {
        this.name = name;
        this.steps = steps;
        this.ingredients = ingredients;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public String getSteps() {
        return steps;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getImageLink() {
        return imageLink;
    }
}
