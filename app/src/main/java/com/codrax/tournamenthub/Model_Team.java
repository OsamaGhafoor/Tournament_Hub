package com.codrax.tournamenthub;

public class Model_Team {
    String name , limit , type;
    String image1;

    public Model_Team() {
    }

    public Model_Team(String name, String limit, String type, String image1) {
        this.name = name;
        this.limit = limit;
        this.type = type;
        this.image1 = image1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }
}

