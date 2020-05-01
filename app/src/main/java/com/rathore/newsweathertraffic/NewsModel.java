package com.rathore.newsweathertraffic;

/**
 * Created by rathore on 08/08/17.
 */

public class NewsModel {
String title;
    String description;
    String urlofImage;

    public String getReadmore() {
        return readmore;
    }

    public void setReadmore(String readmore) {
        this.readmore = readmore;
    }

    String readmore;

//    public NewsModel(String title, String description, String urlofImage) {
//        this.title = title;
//        this.description = description;
//        this.urlofImage = urlofImage;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlofImage() {
        return urlofImage;
    }

    public void setUrlofImage(String urlofImage) {
        this.urlofImage = urlofImage;
    }
}
