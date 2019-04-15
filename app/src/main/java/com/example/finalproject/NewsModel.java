package com.example.finalproject;

public class NewsModel {
    String newstitle;
    String newsdesc;
    String newsweb;
    String imgURL;

    //get and set methods for all variables
    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getNewsdesc() {
        return newsdesc;
    }

    public void setNewsdesc(String newsdesc) {
        this.newsdesc = newsdesc;
    }

    public String getNewsweb() {
        return newsweb;
    }

    public void setNewsweb(String newsweb) {
        this.newsweb = newsweb;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
