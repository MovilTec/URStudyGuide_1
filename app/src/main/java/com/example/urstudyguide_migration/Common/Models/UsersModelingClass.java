package com.example.urstudyguide_migration.Common.Models;

public class UsersModelingClass {

    private String id;
    private String name;
    private String status;
    private String image;
    private String thumb_image;

    public UsersModelingClass(){

    }

    public UsersModelingClass(String id, String name, String status, String image, String thumb_image) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.image = image;
        this.thumb_image = thumb_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
