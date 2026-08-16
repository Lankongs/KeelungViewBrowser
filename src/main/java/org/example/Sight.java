package org.example;

public class Sight {

    private String sightName;
    private String zone;
    private String category;
    private String photoURL;
    private String address;
    private String description;

    // JavaBean 需要無參數建構子
    public Sight() {
    }

    public String getSightName() {
        return sightName;
    }

    public void setSightName(String sightName) {
        this.sightName = sightName;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SightName: " + sightName + "\n" +
                "Zone: " + zone + "\n" +
                "Category: " + category + "\n" +
                "PhotoURL: " + photoURL + "\n" +
                "Description: " + description + "\n" +
                "Address: " + address;
    }
}