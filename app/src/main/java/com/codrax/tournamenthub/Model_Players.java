package com.codrax.tournamenthub;

public class Model_Players {
    String name , email , phone , team_name , type , cat;
    String image1;

    public Model_Players() {
    }

    public Model_Players(String name, String email, String phone, String team_name, String type, String cat, String image1) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.team_name = team_name;
        this.type = type;
        this.cat = cat;
        this.image1 = image1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }
}
