package com.example.krisperezcyrus.lostfound;


import com.google.firebase.database.Exclude;

class LostPost {

    private String name,email,phone,description,image,time,gps,mkey;

    public LostPost(){

    }

    public LostPost(String name, String email, String phone, String description, String image,String time,String gps,String mkey){

        this.name = name;
        this.email =email;
        this.phone = phone;
        this.description = description;
        this.image = image;
        this.time = time;
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

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public String getGps(){
        return gps;
    }


//    @Exclude
//    public String getKey(){
//        return mkey;
//    }





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

    public void setImage(String image) {
        this.image = image;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }


//    @Exclude //not you didn't add THIS to the statement
//    public void setKey(String key) {
//        mkey = key;
//    }
}

