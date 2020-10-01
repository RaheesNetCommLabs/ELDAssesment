package com.example.eldassesment.helper;

public class Image {

    private String dateTime;
    private String status;
    private String thumbnail;
    private String fileSize;
    private int id;


    public Image(String dateTime, String status, String thumbnail, String fileSize, int id) {
        this.dateTime = dateTime;
        this.status = status;
        this.thumbnail = thumbnail;
        this.fileSize = fileSize;
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getFileSize() {
        return fileSize;
    }

}
