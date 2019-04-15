package com.example.finalproject;

public class Definition {

    long id;
    String title;
    String definition;

    public Definition(Long id, String title, String definition) {

        this.id = id;
        this.title = title;
        this.definition = definition;

    }

    public Definition() {

        title = null;
        definition = null;

    }

    public Long getID() {

        return id;
    }


    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }


    public String getDefinition() {

        return definition;
    }

    public void setDefinition(String definition) {

        this.definition = definition;
    }


}