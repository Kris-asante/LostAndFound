package com.example.krisperezcyrus.lostfound;

public class FoundPost {

    private String name,email,phone,image,description,time,gps;

    public FoundPost(){

    }

    public FoundPost(String name, String email, String phone, String description,String image,String time,String gps)
    {

        this.name = name;
        this.email =email;
        this.phone = phone;
        this.description = description;
        this.time = time;
        this.image = image;
        this.gps = gps;

    }

    public String getName(){
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

   public String getImage() {
        return image;
    }


    public String getGps(){
        return gps;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }
}


