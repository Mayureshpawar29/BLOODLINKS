package com.example.bloodlinks;

public class Donor {
    private String name,email,mobile,bloodgroup,gender;
    private Double longitude,latitude;
    public static double longi,lati;

    public Donor(){

    }

    public Donor(String name, String email,String mobile,String bloodgroup, Double latitude, Double longitude, String gender){

        this.name=name;
        this.email=email;
        this.mobile=mobile;
        this.bloodgroup=bloodgroup;
        this.latitude=latitude;
        this.longitude=longitude;
        this.gender=gender;
    }



    public String getMobile() {
        return mobile;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
