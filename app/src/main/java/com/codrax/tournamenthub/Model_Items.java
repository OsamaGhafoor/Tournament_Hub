package com.codrax.tournamenthub;

public class Model_Items {
    String name;
    int image1;

    public Model_Items() {
    }

    public Model_Items(String name, int image1) {
        this.name = name;
        this.image1 = image1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }
}
